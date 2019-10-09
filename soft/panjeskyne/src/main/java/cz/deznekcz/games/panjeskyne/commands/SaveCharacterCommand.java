package cz.deznekcz.games.panjeskyne.commands;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import cz.deznekcz.games.panjeskyne.pages.CharacterPage;

public class SaveCharacterCommand implements Command {

	private CharacterPage page;

	public SaveCharacterCommand(CharacterPage page) {
		this.page = page;
	}

	@Override
	public void menuSelected(MenuItem selectedItem) {
		page.saveCharacter();
	}

}
