package cz.panjeskyne.service.impl;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import cz.panjeskyne.model.xml.Skill;
import cz.panjeskyne.model.xml.SkillGroup;
import cz.panjeskyne.service.SkillGroupService;
import cz.panjeskyne.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {
	
	private ImmutableMap<String, Skill> skills;
	
	@Autowired
	private SkillGroupService skillGroupService;
	
	@PostConstruct
	private void init() {
		ImmutableMap.Builder<String, Skill> builder = ImmutableMap.<String, Skill>builder();
		for (SkillGroup race : skillGroupService.getAll()) {
			builder.putAll(race.getSkills());
		}
		skills = builder.build();
	}

	@Override
	public Collection<Skill> getAll() {
		return skills.values();
	}

	public Skill getByCodename(String codename) {
		return skills.get(codename);
	}
}
