package cz.deznekcz.games.panjeskyne.model.xml.adapter;

import java.util.HashMap;

import cz.deznekcz.games.panjeskyne.model.xml.Skill;
import cz.deznekcz.games.panjeskyne.model.xml.Skills;

public class SkillMapAdapter extends ListToMapAdapter<String, Skill, Skills, Skill> {

	@Override
	public Skills marshal(HashMap<String, Skill> v) throws Exception {
		return build(Skills::new, v);
	}

}
