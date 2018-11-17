package cz.panjeskyne.service;

import cz.panjeskyne.model.CharacterSkill;

public interface SkillService {

	static CharacterSkill getSkill(Object characterId) {
		return new CharacterSkill();
	}

}
