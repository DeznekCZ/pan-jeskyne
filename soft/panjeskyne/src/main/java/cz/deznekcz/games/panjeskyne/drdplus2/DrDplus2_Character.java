package cz.deznekcz.games.panjeskyne.drdplus2;

import cz.deznekcz.games.panjeskyne.data.Abstract_Character;
import cz.deznekcz.games.panjeskyne.data.Character;

public class DrDplus2_Character extends Abstract_Character {
	
	public static interface Variables {
		public static interface Background {
			String TYPE   = "background.type";
			String LEVEL  = "background.level";
			String SKILLS = "background.skills";
			
			static String STATISTIC(String statistic) {
				return "background." + statistic;
			}
		}
		String EXPERIENCE = "experience";
		String LEVEL      = "level";
	}
	
	public DrDplus2_Character(Character data) {
		super(data, DrDplus2.ID);
	}
	
	public int getExperience() {
		return super.getInteger(Variables.EXPERIENCE);
	}
	
	public void setExperience(int experience) {
		super.setInteger(Variables.EXPERIENCE, experience);
	}
	
	public int getLevel() {
		return super.getInteger(Variables.LEVEL);
	}
	
	public void setLevel(int level) {
		super.setInteger(Variables.LEVEL, level);
	}
}
