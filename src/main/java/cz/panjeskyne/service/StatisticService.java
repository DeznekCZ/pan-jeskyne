package cz.panjeskyne.service;

import java.util.ArrayList;
import java.util.List;

import cz.panjeskyne.form.StatisticForm;
import cz.panjeskyne.model.Statistic;

public interface StatisticService extends BaseService<Statistic, StatisticForm>{

	static List<Statistic> getStatisticsWhereFormulaContains(String codename) {
		return new ArrayList<>();
	}

}