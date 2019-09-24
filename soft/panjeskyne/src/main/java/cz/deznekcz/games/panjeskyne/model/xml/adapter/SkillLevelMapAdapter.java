package cz.deznekcz.games.panjeskyne.model.xml.adapter;

import java.util.HashMap;

import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillLevel;
import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillLevels;

public class SkillLevelMapAdapter extends ListToMapAdapter<Integer, SkillLevel, SkillLevels, SkillLevel> {

	@Override
	public SkillLevels marshal(HashMap<Integer, SkillLevel> v) throws Exception {
		return build(SkillLevels::new, v);
	}

}
