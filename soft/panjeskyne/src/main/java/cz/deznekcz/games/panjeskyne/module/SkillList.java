package cz.deznekcz.games.panjeskyne.module;

import java.util.ArrayList;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Skill;
import cz.deznekcz.games.panjeskyne.service.formula.Result;

public class SkillList extends Window implements ModuleConstants {

	private AModule module;
	private Character character;
	private Runnable refreshOnAdd;
	private Accordion list;

	public SkillList(AModule module, Character character, Runnable refreshOnAdd) {
		this.module = module;
		this.character = character;
		this.refreshOnAdd = refreshOnAdd;
		
		setCaption("Nové dovednosti");
		
		list = new Accordion();
		setContent(list);
		
		refreshSkills();
	}

	@Override
	public void focus() {
		refreshSkills();
		super.focus();
	}

	public void refreshSkills() {
//		ArrayList<Skill> allSkills = new ArrayList<>(module.getSkillService().getAll());
//		ArrayList<Skill> filteredSkills = new ArrayList<>();
////		ArrayList<SkillData> characterSkills = new ArrayList<>(module.getCharacterService().getCharacterSkills(character));
//		
////		for (Skill skill : allSkills) {
////			if (skill.isHidden()) continue;
////			
////			boolean known = false;
////			for (SkillData skillData : characterSkills) {
////				if (skillData.isFound() && !known) {
////					known |= skillData.getRef().equals(skill.getId());
////					if (known) break;
////				}
////			}
////			if (!known) {
////				filteredSkills.add(skill);
////			}
////		}
//		
//		list.removeAllComponents();
//		filteredSkills.sort((_1, _2) -> _1.getName().compareTo(_2.getName()));
//		
//		for (Skill skill : filteredSkills) {
//			VerticalLayout vl = new VerticalLayout();
//			vl.setWidth(FILL);
//			
//			Tab tab = list.addTab(vl, skill.getName());
//			
//			Button addSkill = new Button("Přidat");
//			addSkill.addClickListener(event -> {
//				list.removeTab(tab);
////				character.learn(skill.getId(), 1);
//				refreshOnAdd.run();
//			});
//			vl.addComponent(addSkill);
//			
//			TextArea desc = new TextArea("Popis");
//			desc.setValue(skill.getDesc());
//			desc.setReadOnly(true);
//			desc.setWidth(FILL);
//			vl.addComponent(desc);
//		}
	}
}
