package cz.panjeskyne.service;

import java.util.Collection;
import java.util.List;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Statistic;

public interface StatisticService {

	Statistic getByCodename(String codename);

	List<Statistic> getDependentStatistics(String codename);

	Collection<Statistic> getAll();

	Result getFormulaValue(Character character, String formula);

	Result getValue(Character character, Statistic statistic);
	
	default Result getValue(Character character, String codename) {
		return getValue(character, getByCodename(codename));
	}
	
	Result validateFormula(String formula);
}