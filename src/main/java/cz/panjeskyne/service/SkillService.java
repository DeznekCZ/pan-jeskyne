package cz.panjeskyne.service;

import cz.panjeskyne.model.db.CharacterSkill;

public interface SkillService {

	static CharacterSkill getSkill(Object characterId) {
		return new CharacterSkill();
	}

}
