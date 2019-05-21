package cz.panjeskyne.service;

import java.util.Collection;
import java.util.List;

import cz.panjeskyne.i18n.I18N;
import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Statistic;
import cz.panjeskyne.service.formula.FormulaException;
import cz.panjeskyne.service.formula.Result;

public interface StatisticService {

	Statistic getByCodename(String codename);

	List<Statistic> getDependentStatistics(String codename);

	Collection<Statistic> getAll();

	Result getFormulaValue(Character character, String formula);

	Result getValue(Character character, Statistic statistic);
	
	default Result getValue(Character character, String codename) {
		Statistic statistic = getByCodename(codename);
		if (statistic == null) {
			Result r = new Result();
			r.setException(new FormulaException(I18N.argumented(I18N.DATA_NOT_FOUND, I18N.id(codename))));
			return r;
		} else {
			return getValue(character, statistic);
		}
	}
	
	Result validateFormula(String formula);

	Collection<Statistic> getStatisticsByGroup(String string);
}