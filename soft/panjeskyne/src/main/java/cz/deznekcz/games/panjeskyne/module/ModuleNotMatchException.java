package cz.deznekcz.games.panjeskyne.module;

@SuppressWarnings("serial")
public class ModuleNotMatchException extends ModuleException {

	public ModuleNotMatchException(String id, Class<? extends AModule> clazz) {
		super("Datový typ požadovaného mudulu neodpovídá: " + id + " <> " + clazz.getName());
	}

}
