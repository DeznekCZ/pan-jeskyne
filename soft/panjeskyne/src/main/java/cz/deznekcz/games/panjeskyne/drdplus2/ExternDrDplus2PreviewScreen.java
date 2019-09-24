package cz.deznekcz.games.panjeskyne.drdplus2;

import java.util.function.Consumer;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.module.ModuleConstants;
import cz.deznekcz.games.panjeskyne.user.User;

public class ExternDrDplus2PreviewScreen extends Window implements ModuleConstants {

	private static final long serialVersionUID = 8921241245289795104L;

	public ExternDrDplus2PreviewScreen(DrDplus2 module, User viewingUser, Character character) {
		setCaption("Postava: " + character.getName());
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
					Consumer<TextField> onAdd = with -> {
						with.setWidth(FILL);
					};
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
						
//						String raceName = DrDplus2.tryGet("Neznámá", () -> module.getKindService().getByCodename(character.getKindCodename()).getName());
//						int level = DrDplus2.tryGet(0, () -> (int) module.getStatisticService().getValue(character, "character.level").getValue() );
//						int exp = DrDplus2.tryGet(0, () -> (int) module.getStatisticService().getValue(character, "character.experience").getValue() );

//						infoLayout.addComponent(new Label("Rasa: " + raceName));
//						infoLayout.addComponent(new Label("Úroveň: " + level));
//						infoLayout.addComponent(new Label("Zkušenost: " + exp));
						
						TextArea infoArea = new TextArea();
						infoArea.setWidth(FILL);
						infoArea.setReadOnly(true);
						infoArea.setCaption("Poznámky");
						infoArea.setValue(character.getDescription());
						infoLayout.addComponent(infoArea);
						infoLayout.setExpandRatio(infoArea, 1);
					}
					{
//						Accordion accordion = new Accordion();
//						tabSheet.addTab(accordion, "Dovednosti");
//						List<SkillData> skillList = module.getCharacterService().getCharacterSkills(character);
//						for (SkillData skillData : skillList) {
//							Skill skill = skillData.getSkill();
//							if (skill != null) {
//								TextArea skillInfo = new TextArea();
//								skillInfo.setWidth(FILL);
//								skillInfo.setReadOnly(true);
//								skillInfo.setValue(skill.getDesc());
//						
//								Tab tab = accordion.addTab(skillInfo, skill.getName() + ": " + DrDplus2.numberToIII(skillData.getLevel()));
//								
//							} else {
//								Tab tab = accordion.addTab(new HorizontalLayout(), "Neznámá dovednost: " + DrDplus2.numberToIII(skillData.getLevel()));
//								tab.setEnabled(false);
//							}
//						}
					}
					{
						tabSheet.addTab(new VerticalLayout(), "Souboj");
					}
				}
			}
		}
	}

}
