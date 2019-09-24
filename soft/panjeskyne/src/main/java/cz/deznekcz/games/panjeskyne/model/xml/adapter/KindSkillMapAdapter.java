package cz.deznekcz.games.panjeskyne.model.xml.adapter;

import java.util.HashMap;

import cz.deznekcz.games.panjeskyne.model.xml.skill.KindSkill;
import cz.deznekcz.games.panjeskyne.model.xml.skill.KindSkills;

public class KindSkillMapAdapter extends ListToMapAdapter<String, KindSkill, KindSkills, KindSkill> {

	@Override
	public KindSkills marshal(HashMap<String, KindSkill> v) throws Exception {
		return build(KindSkills::new, v);
	}

}
