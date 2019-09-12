package cz.deznekcz.games.panjeskyne.module;

@SuppressWarnings("serial")
public class ModuleNotFoundException extends ModuleException {
	public ModuleNotFoundException(String id) {
		super("Modul nenalezen!: " + id);
	}
}
