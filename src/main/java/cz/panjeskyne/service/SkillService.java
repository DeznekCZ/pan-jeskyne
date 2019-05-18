package cz.panjeskyne.service;

import java.util.Collection;

import cz.panjeskyne.model.xml.Skill;

public interface SkillService {
	Collection<Skill> getAll();

	double getAdditionBonus(String skill, int level, String statistic);
	double getMultiplyBonus(String skill, int level, String statistic);
}
