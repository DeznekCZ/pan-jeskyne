package cz.panjeskyne.service;

import java.util.Collection;
import java.util.List;

import cz.panjeskyne.model.xml.Statistic;

public interface StatisticService {

	Statistic getByCodename(String codename);

	List<Statistic> getDependentStatistics(String codename);

	Collection<Statistic> getAll();

}