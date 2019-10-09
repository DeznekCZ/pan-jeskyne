package cz.deznekcz.games.panjeskyne.commands;

import java.io.IOException;

import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import cz.deznekcz.games.panjeskyne.Redirection;
import cz.deznekcz.serialized.ObjectReader;
import cz.deznekcz.serialized.ObjectWriter;
import cz.deznekcz.serialized.Serialized;

public class NewCharacterCommand implements Command, Serialized {

	private static final long serialVersionUID = 1L;

	@Override
	public void menuSelected(MenuItem selectedItem) {
		Redirection.newCharacterCreation();
	}

	@Override
	public void store(ObjectWriter writer) throws IOException {
		
	}

	@Override
	public void load(ObjectReader reader) throws IOException, ClassNotFoundException {
		
	}

}
