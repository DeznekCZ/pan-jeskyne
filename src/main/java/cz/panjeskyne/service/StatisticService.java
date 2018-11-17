package cz.panjeskyne.service;

import java.util.ArrayList;
import java.util.List;

import cz.panjeskyne.form.StatisticForm;
import cz.panjeskyne.model.Statistic;

public interface StatisticService extends BaseFormService<Statistic, StatisticForm>{

	static List<Statistic> getStatisticsWhereFormulaContains(String codename) {
		return new ArrayList<>();
	}

	static Statistic getStatistic(String codename) {
		// TODO vracet null pokud nic nenalezeno
		Statistic statistic = new Statistic();
		statistic.setCodename(codename);
		return statistic;
	}

}