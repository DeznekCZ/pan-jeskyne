package cz.deznekcz.games.panjeskyne.drdplus2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.vaadin.ui.Window;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.module.AModule;
import cz.deznekcz.games.panjeskyne.module.ModuleException;
import cz.deznekcz.games.panjeskyne.user.User;

public class DrDplus2 extends AModule {

	public static final String ID = "drdplus2";

	protected DrDplus2() {
		super(ID, "Dačí doupě + 2.0");
	}

	@Override
	public Window getCharacterCreationScreen(Runnable onCreated) {
		return new ExternDrDplus2CreationScreen(this, onCreated);
	}

	@Override
	public Window getCharacterPreviewScreen(User viewingUser, Character character) {
		return new ExternDrDplus2PreviewScreen(this, viewingUser, character);
	}

	@Override
	public Window getCharacterEditScreen(Runnable onSaved, Character character) {
		// TODO Auto-generated method stub
		return null;
	}

	public static DrDplus2 getModule() {
		if (!getModules().containsKey(ID))
		{
			return new DrDplus2();
		} else {
			try {
				return getModule(ID);
			} catch (ModuleException e) {
				return new DrDplus2();
			}
		}
	}

	@Override
	protected void storeModuleToCache(ObjectOutputStream out) {
		
	}

	@Override
	protected void readModuleFromCache(ObjectInputStream in) {
		
	}

}
