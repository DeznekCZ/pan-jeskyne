package cz.panjeskyne.model;

import cz.panjeskyne.service.I18NService;

public class Skill {
	private static final String NAME = "%s.NAME";
	private static final String DESCRIPTION = "%s.DESC.%d";
	
	private String codename;
	private int maxLevel;
	private boolean hidden;
	
	public String getDescription(String locale, int level) {
		return I18NService.getString(locale, 
				String.format(DESCRIPTION, codename, level));
	}
	
	public String getName(String locale) {
		return I18NService.getString(locale, 
				String.format(NAME, codename));
	}
	
	public void setCodename(String codename) {
		this.codename = codename;
	}
	
	public String getCodename() {
		return codename;
	}
	
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public boolean isHidden() {
		return hidden;
	}
}
