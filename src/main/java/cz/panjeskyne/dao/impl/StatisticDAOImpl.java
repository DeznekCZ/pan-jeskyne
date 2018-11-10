package cz.panjeskyne.dao.impl;

import org.springframework.stereotype.Repository;

import cz.panjeskyne.dao.AbstractBaseDAO;
import cz.panjeskyne.dao.StatisticDAO;
import cz.panjeskyne.model.Statistic;

@Repository
public class StatisticDAOImpl extends AbstractBaseDAO<Statistic> implements StatisticDAO {
	
	public StatisticDAOImpl() {
		super(Statistic.class);
	}
}
