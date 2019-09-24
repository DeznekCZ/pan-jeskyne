package cz.deznekcz.games.panjeskyne.drdplus2;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.module.AModule;

public class SkillInfo extends HorizontalLayout implements Button.ClickListener {
	
	private static final long serialVersionUID = -3516733676216395670L;
	
	public static String numberToIII(int level) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < level; i++) {
			stringBuilder.append('I');
		}
		return stringBuilder.toString();
	}
	
	// Module data
	//private SkillData skill;
	private Character character;
	
	// Layout
	private Button addButton;
	private Button removeButton;
	private Button infoButton;
	private Label label;
	
	// ChangeListener
	private Runnable onChange;

	// Private flow
	private boolean enableRemove;
	private boolean enableAdd;
	private int startLevel;
	
	public SkillInfo(Character character, AModule module, String ref, int level, Runnable onChange) {
		this.character = character;
		//this.skill = new SkillData(ref, level, module);
		this.onChange = onChange;
		//this.startLevel = skill.getLevel();
		//this.enableAdd = skill.getSkill().getLimit() > startLevel;
		this.enableRemove = startLevel > 1;
		
		label = new Label();
		addComponent(label);
		setExpandRatio(label, 1);
		updateLabel();
		
		addButton = new Button("+");
		addButton.addClickListener(this);
		addComponent(addButton);
		
		removeButton = new Button("-");
		removeButton.addClickListener(this);
		addComponent(removeButton);
		
		infoButton = new Button("?");
		//infoButton.setDescription(skill.getSkill().getDesc());
		addComponent(infoButton);
		
		setMargin(false);
		setSpacing(false);
	}

	@Override
	public void buttonClick(ClickEvent event) {
//		if (event.getButton() == addButton) {
//			skill.setLevel(skill.getLevel() + 1);
//			enableRemove = true;
//			if (skill.getSkill().getLimit() == skill.getLevel()) {
//				enableAdd = false;
//			}
//		} else if (event.getButton() == removeButton) {
//			skill.setLevel(skill.getLevel() - 1);
//			enableAdd = true;
//			if (startLevel == skill.getLevel()) {
//				enableRemove = false;
//			}
//		}
//		character.learn(skill.getRef(), skill.getLevel());
		onChange.run();
		updateLabel();
	}
	
	private void updateLabel() {
		//label.setCaption(skill.getSkill().getName() + ": " + numberToIII(skill.getLevel()));
	}

	public void setCanIncrease(boolean can) {
		addButton.setEnabled(can && enableAdd);
		removeButton.setEnabled(enableRemove);
	}

	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
		//this.enableAdd = skill.getSkill().getLimit() > startLevel;
	}

	public int getStartLevel() {
		return startLevel;
	}
}
