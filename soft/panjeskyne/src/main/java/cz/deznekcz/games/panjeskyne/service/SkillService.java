package cz.deznekcz.games.panjeskyne.service;

import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import cz.deznekcz.games.panjeskyne.model.xml.Bonus;
import cz.deznekcz.games.panjeskyne.model.xml.Skill;
import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillGroup;
import cz.deznekcz.games.panjeskyne.module.AModule;
import cz.deznekcz.games.panjeskyne.service.SkillGroupService;
import cz.deznekcz.games.panjeskyne.service.SkillService;

public class SkillService {
	
	private Map<String, Skill> skills;
	
	private SkillGroupService skillGroupService;

	private Skill lastSkill;

	private AModule module;
	
	public SkillService(AModule module) {
		this.module = module;
		skillGroupService = new SkillGroupService(module);
		
		skills = Maps.newHashMap();
		
		for (SkillGroup race : skillGroupService.getAll()) {
			skills.putAll(race.getSkills());
		}
	}

	public Collection<Skill> getAll() {
		return skills.values();
	}

	public Skill getByCodename(String codename) {
		return skills.get(codename);
	}

	public synchronized double getAdditionBonus(String skill, int level, String statistic) {
		if (lastSkill == null || !lastSkill.getId().equals(skill)) {
			lastSkill = getByCodename(skill);
			if (lastSkill == null)
				throw new RuntimeException("Missing skill: " + skill);
		}
		return lastSkill.getSkillSimpleBonus(statistic, level).getAddition()
			 + lastSkill.getSkillLevelBonus(statistic, level).getAddition();
	}

	public synchronized double getMultiplyBonus(String skill, int level, String statistic) {
		if (lastSkill == null || !lastSkill.getId().equals(skill)) {
			lastSkill = getByCodename(skill);
		}
		return lastSkill.getSkillSimpleBonus(statistic, level).getMultiply()
		     + lastSkill.getSkillLevelBonus(statistic, level).getMultiply();
	}
	
	public SkillGroupService getSkillGroupService() {
		return skillGroupService;
	}
}
