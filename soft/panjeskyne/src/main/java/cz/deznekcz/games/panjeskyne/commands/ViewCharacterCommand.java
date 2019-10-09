package cz.deznekcz.games.panjeskyne.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Grid;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import cz.deznekcz.games.panjeskyne.Redirection;
import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.pages.Dialogs;
import cz.deznekcz.games.panjeskyne.pages.Dialogs.Type;
import cz.deznekcz.games.panjeskyne.service.CharacterFilter;
import cz.deznekcz.games.panjeskyne.service.CharacterService;
import cz.deznekcz.games.panjeskyne.service.WorldService;
import cz.deznekcz.games.panjeskyne.user.User;
import cz.deznekcz.serialized.ObjectReader;
import cz.deznekcz.serialized.ObjectWriter;
import cz.deznekcz.serialized.Serialized;
import cz.deznekcz.util.data.Quartet;

public class ViewCharacterCommand implements Command, Serialized, CharacterFilter {

	private static final long serialVersionUID = 1L;
	private User user;

	@Override
	public void store(ObjectWriter writer) throws IOException {
		
	}

	@Override
	public void load(ObjectReader reader) throws IOException, ClassNotFoundException {
		
	}

	@Override
	public void menuSelected(MenuItem selectedItem) {
		user = selectedItem.getMenuBar().getUI().getSession().getAttribute(User.class);
		List<Character> characters = CharacterService.getInstance().getAll(this);
		user = null;
		
		Grid<Quartet<String, String, String, String>> content = new Grid<>();
		content.addColumn(Quartet::getB).setCaption("Postava");
		content.addColumn(Quartet::getD).setCaption("Svět");
		
		List<Quartet<String, String, String, String>> data = new ArrayList<>();
		
		for (Character character : characters) {
			if (character.getEditors().contains(user.getUserName()))
				data.add(new Quartet<>(character.getId(), character.getName(), character.getId(), WorldService.getInstance().getWorld(character.getWorldId()).getName()));
		}
		
		Dialogs.ask(Type.OK_CANCEL)
			.content(content)
			.onOk((dialog) -> {
				Quartet<String, String, String, String> value = content.asSingleSelect().getValue();
				if (value == null) dialog.show();
				else {
					Redirection.newCharacter(value.getA());
				}
			})
			.show();
	}

	@Override
	public boolean filter(Character character) {
		return character.getEditors().contains(user.getUserName());
	}

}
