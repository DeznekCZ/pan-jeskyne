package cz.deznekcz.games.panjeskyne.drdplus2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.TabSheet.Tab;

import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Kind;
import cz.deznekcz.games.panjeskyne.model.xml.Race;
import cz.deznekcz.games.panjeskyne.model.xml.Skill;
import cz.deznekcz.games.panjeskyne.model.xml.Statistic;
import cz.deznekcz.games.panjeskyne.model.xml.skill.CharacterSkill;
import cz.deznekcz.games.panjeskyne.model.xml.skill.KindSkill;
import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillGroup;
import cz.deznekcz.games.panjeskyne.module.ModuleConstants;
import cz.deznekcz.games.panjeskyne.module.SkillList;
import cz.deznekcz.games.panjeskyne.service.formula.Pair;
import cz.deznekcz.games.panjeskyne.service.formula.Result;
import cz.deznekcz.games.panjeskyne.service.helper.SkillData;

public class ExternDrDplus2CreationScreen extends Window implements ModuleConstants {

	private static final long serialVersionUID = -8719849467723253771L;
	
	private Character character;

	private TextField name;

	private TextArea infoArea;

	private ComboBox<Race> race;

	private ComboBox<Kind> kind;

	private Map<String, Pair<TextField, Statistic>> statisticFields;
	
	private DrDplus2 module;

	private List<SkillInfo> increaseAble;

	private Runnable onCreated;

	private VerticalLayout skillLayout;

	private VerticalLayout kindSkills;

	private Label skillCount;

	private boolean skillsLoaded;

	public ExternDrDplus2CreationScreen(DrDplus2 module, Runnable onCreated) {
		this.module = module;
		this.onCreated = onCreated;
		open();
	}
	
	private void open() {
		setCaption("Tvorba postavy");
		character = new Character();
		character.setKind(Kind.EMPTY);
		
		Consumer<TextField> onAdd = with -> {
			with.setWidth(FILL);
			statisticFields.put(((Statistic) with.getData()).getId(), new Pair<>(with, (Statistic)with.getData()));
		};
		
		statisticFields = Maps.newHashMap();
		increaseAble = Lists.newArrayList();
		
		setWidth("800px");
		setHeight("600px");
		
		{// Main layout diviation
			HorizontalLayout mainDiv = new HorizontalLayout();
			mainDiv.setMargin(false);
			mainDiv.setWidth(FILL);
			setContent(mainDiv);
			{// Portrait layout
				VerticalLayout portraitLayout = new VerticalLayout();
				portraitLayout.setWidth(PANEL_WIDTH);
				
				mainDiv.addComponent(portraitLayout);
				{// Character portrait
					Image portrait = new Image("");
					portrait.setHeight(PANEL_WIDTH);
					portrait.setWidth(PANEL_WIDTH);
					portraitLayout.addComponent(portrait);
				}
				
				{// Main stats preview
					VerticalLayout mainStats = new VerticalLayout();
					module.add(mainStats, module.getStatisticAsField("statistic.sil", character, false, null), onAdd);
					module.add(mainStats, module.getStatisticAsField("statistic.obr", character, false, null), onAdd);
					module.add(mainStats, module.getStatisticAsField("statistic.zrc", character, false, null), onAdd);
					module.add(mainStats, module.getStatisticAsField("statistic.vol", character, false, null), onAdd);
					module.add(mainStats, module.getStatisticAsField("statistic.int", character, false, null), onAdd);
					module.add(mainStats, module.getStatisticAsField("statistic.chr", character, false, null), onAdd);
					
					mainStats.setHeightUndefined();
					mainStats.setMargin(false);
					portraitLayout.addComponent(mainStats);
					portraitLayout.setExpandRatio(mainStats, 1);
				}
			}
			
			{// Layout of main page
				VerticalLayout verticalLayout = new VerticalLayout();
				verticalLayout.setMargin(false);
				mainDiv.addComponent(verticalLayout);
				mainDiv.setExpandRatio(verticalLayout, 1);
				
				{// 
					TabSheet tabSheet = new TabSheet();
					tabSheet.setWidth(FILL);
					verticalLayout.addComponent(tabSheet);
					verticalLayout.setExpandRatio(tabSheet, 1);
					{
						VerticalLayout infoLayout = new VerticalLayout();
						infoLayout.setWidth(FILL);
						tabSheet.addTab(infoLayout, "Popis");
						{// Name field
							name = new TextField();
							name.setCaption("Jméno");
							name.setWidth(FILL);
							name.addValueChangeListener(event -> {
								character.setName(event.getValue());
								checkSaveAble();
							});
							infoLayout.addComponent(name);
						}
						{// Race, Kind
							race = new ComboBox<>("Rasa");
							race.setWidth(FILL);
							race.addValueChangeListener(event -> {
								List<Kind> kinds = new ArrayList<>(event.getValue().getKinds().values());
								kinds.sort((_1,_2) -> _1.getName().compareTo(_2.getName()));
								kind.setItems(kinds);
								kind.setValue(Kind.EMPTY);
							});
							race.setEmptySelectionAllowed(false);
							List<Race> races = new ArrayList<>(module.getRaceService().getAll());
							races.sort((_1,_2) -> _1.getName().compareTo(_2.getName()));
							race.setItems(races);
							race.setItemCaptionGenerator(Race::getName);
							
							kind = new ComboBox<>("Druh");
							kind.setWidth(FILL);
							kind.addValueChangeListener(event -> {
								character.setKind(event.getValue());
								refreshSkills();
								checkSaveAble();
							});
							kind.setItemCaptionGenerator(Kind::getName);
							kind.setEmptySelectionAllowed(false);
							infoLayout.addComponent(race);
							infoLayout.addComponent(kind);
						}
						{// Description
							infoArea = new TextArea();
							infoArea.setWidth(FILL);
							infoArea.setCaption("Poznámky");
							infoArea.addValueChangeListener(event -> {
								character.setDescription(event.getValue());
								checkSaveAble();
							});
							infoLayout.addComponent(infoArea);
							infoLayout.setExpandRatio(infoArea, 1);
						}
					}
					{
						skillLayout = new VerticalLayout();
						skillLayout.setWidth(FILL);
						
						skillCount = new Label();
						skillLayout.addComponent(skillCount);
						
						Tab tabSkills = tabSheet.addTab(skillLayout, "Dovednosti");
						
						refreshSkills();
					}
					{
						tabSheet.addTab(new VerticalLayout(), "Výbava");
					}
				}
			}
		}
	}

	private void refreshSkills() {
		
		if (kind.getValue() != null && kind.getValue().hasSkills()) {
			if (kindSkills == null) {
				kindSkills = new VerticalLayout();
				kindSkills.setMargin(new MarginInfo(false, false, false, true));
				kindSkills.setSpacing(false);
				kindSkills.setCaption("Dovednosti rasy");
				skillLayout.addComponent(kindSkills, 1);
			} else {
				kindSkills.setVisible(true);
			}
			
			kindSkills.removeAllComponents();
			List<KindSkill> ks = Lists.newArrayList(kind.getValue().getSkills().values());
			for (KindSkill s : ks) {
				SkillInfo si = new SkillInfo(character, module, s.getRef(), s.getLevel(), this::refreshSkills);
				si.setWidth(FILL);
				increaseAble.add(si);
				kindSkills.addComponent(si);
			}
		} else {
			if (kindSkills != null) {
				kindSkills.forEach(increaseAble::remove);
				kindSkills.removeAllComponents();
				kindSkills.setVisible(false);
			}
		}
		
		if (!skillsLoaded) {
			List<CharacterSkill> chs = Lists.newArrayList(character.getSkills());
			List<SkillGroup> sg =  Lists.newArrayList(module.getSkillGroupService().getAll());
			
			for (SkillGroup sgi : sg) {
				VerticalLayout groupLayout = new VerticalLayout();
				groupLayout.setCaption(sgi.getName());
				groupLayout.setMargin(new MarginInfo(false, false, false, true));
				groupLayout.setSpacing(false);
				
				List<Skill> s = Lists.newArrayList(sgi.getSkills().values());
				for (Skill si : s) {
					SkillInfo skillInfo = new SkillInfo(character, module, si.getKey(), 0, this::refreshStatistics);
					skillInfo.setWidth(FILL);
					
					if (!chs.isEmpty()) {
						for (CharacterSkill chsi : chs) {
							if (chsi.getRef().equals(si.getId())) {
								chs.remove(chsi);
								skillInfo.setStartLevel(chsi.getLevel());
								break;
							}
						}
					}
					
					if (!si.isHidden() || skillInfo.getStartLevel() > 0) {
						groupLayout.addComponent(skillInfo);
						increaseAble.add(skillInfo);
					}
				}
				
				if (groupLayout.getComponentCount() > 0) {
					skillLayout.addComponent(groupLayout);
				}
			}
			
			skillsLoaded = true;
		}
		
		refreshStatistics();
	}

	private void refreshSkillAddition() {
		Result result = module.getStatisticService().getValue(character, "skills.learnable");
		if (!result.isSuccessful()) {
			result.getException().printStackTrace(System.err);
		}
		if (result.getIntValue() == 0) {
			skillCount.setCaption("Dovednosti plné");
			
			for (SkillInfo button : increaseAble) {
				button.setCanIncrease(false);
			}
		} else if (result.getIntValue() < 0) {
			skillCount.setCaption("Je nutno se odnaučit: " + (0 - result.getIntValue()));
			
			for (SkillInfo button : increaseAble) {
				button.setCanIncrease(false);
			}
		} else /* if (result.getIntValue() > 0) */ {
			skillCount.setCaption("Volné body dovedností: " + result.getIntValue());
			
			for (SkillInfo button : increaseAble) {
				button.setCanIncrease(true);
			}
		}
	}

	private void refreshStatistics() {
		statisticFields.forEach((key, value) -> {
			Result result = module.getStatisticService().getValue(character, value.getValue());
			value.getKey().setValue(result.getValueAsString(false));
		});	
		
		refreshSkillAddition();
	}

	private void checkSaveAble() {
		
	}

	@Override
	public void close() {
		super.close();
	}

}
