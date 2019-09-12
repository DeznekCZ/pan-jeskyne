package cz.deznekcz.games.panjeskyne.service;

import java.util.Collection;

import com.google.common.collect.ImmutableMap;

import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillGroup;

public class SkillGroupService {

	private final ImmutableMap<String, SkillGroup> skillGroups = SkillLoader.loadSkills();

	public Collection<SkillGroup> getAll() {
		return skillGroups.values();
	}
}
