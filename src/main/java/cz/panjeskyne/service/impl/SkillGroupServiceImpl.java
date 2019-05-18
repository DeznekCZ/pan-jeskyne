package cz.panjeskyne.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import cz.panjeskyne.model.xml.skill.SkillGroup;
import cz.panjeskyne.service.SkillGroupService;

@Service
public class SkillGroupServiceImpl implements SkillGroupService {

	private final ImmutableMap<String, SkillGroup> skillGroups = SkillLoader.loadSkills();

	@Override
	public Collection<SkillGroup> getAll() {
		return skillGroups.values();
	}
}
