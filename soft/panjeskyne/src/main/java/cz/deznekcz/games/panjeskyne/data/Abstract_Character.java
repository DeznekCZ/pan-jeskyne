package cz.deznekcz.games.panjeskyne.data;

public class Abstract_Character {
	
	private final Character characterData;
	private final String moduleId;
	private final CharacterRulesData moduleData;
	
	public Abstract_Character(Character characterData, String id) {
		this.moduleId = id;
		this.characterData = characterData;
		this.moduleData = characterData.getData(id);
	}

	public int getInteger(String key) {
		return moduleData.getInteger(key);
	}

	public void setInteger(String key, int value) {
		moduleData.setInteger(key, value);
	}

	public double getDouble(String key) {
		return moduleData.getDouble(key);
	}

	public void setDouble(String key, double value) {
		moduleData.setDouble(key, value);
	}

	public String getString(String key) {
		return moduleData.getString(key);
	}

	public void setString(String key, String value) {
		moduleData.setString(key, value);
	}
	
	public String getName() {
		return characterData.getName();
	}
	
	public String getDescritpion() {
		return characterData.getDescription();
	}
}
