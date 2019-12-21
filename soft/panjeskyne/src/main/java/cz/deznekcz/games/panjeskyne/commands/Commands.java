package cz.deznekcz.games.panjeskyne.commands;

import com.vaadin.ui.MenuBar.Command;

import cz.deznekcz.games.panjeskyne.pages.CharacterPage;

public class Commands {

	public static Command newCharacter() {
		return new NewCharacterCommand();
	}

	public static Command viewCharacter() {
		return new ViewCharacterCommand();
	}

	public static Command saveCharacter(CharacterPage page) {
		return new SaveCharacterCommand(page);
	}

	public static Command listSkills() {
		return new SkillListCommand();
	}

	public static Command newSkills() {
		return new NewListCommand();
	}

}
