package cz.deznekcz.games.panjeskyne.service;

import java.util.ArrayList;
import java.util.List;

import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Kind;
import cz.deznekcz.games.panjeskyne.module.AModule;
import cz.deznekcz.games.panjeskyne.service.helper.SkillData;

public class CharacterService {
	
	private AModule module;

	public CharacterService(AModule module) {
		this.module = module;
	}
	
	public List<SkillData> getCharacterSkills(Character character) {
		List<SkillData> skills = new ArrayList<>();
		
		Kind kind = module.getKindService().getByCodename(character.getKindCodename());
		if (kind != null)
			kind.getSkills()
					.values()
					.stream()
					.map(skill -> new SkillData(skill.getRef(), skill.getLevel(), module))
					.sorted()
					.forEach(skills::add);
				
		character.getSkills()
				.stream()
				.map(skill -> new SkillData(skill.getRef(), skill.getLevel(), module))
				.sorted()
				.forEach(skill -> {
					int ind = skills.indexOf(skill);
					if (ind == -1) {
						skills.add(skill);
					} else {
						SkillData sk = skills.get(ind);
						sk.setLevel(Math.max(skill.getLevel(), sk.getLevel()));
					}
				});
		
		return skills;
	}

	public void learn(Character character, String id, int level) {
		character.learn(id, level);
	}
}
