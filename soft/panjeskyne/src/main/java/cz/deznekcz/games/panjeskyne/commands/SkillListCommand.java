package cz.deznekcz.games.panjeskyne.commands;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import cz.deznekcz.games.panjeskyne.Redirection;

public class SkillListCommand implements Command {

	@Override
	public void menuSelected(MenuItem selectedItem) {
		Redirection.newPage("/skill/all", "skill_all");
	}

}
