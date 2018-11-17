package cz.panjeskyne.dao.impl;

import org.springframework.stereotype.Repository;

import cz.panjeskyne.dao.AbstractBaseDAO;
import cz.panjeskyne.dao.RaceDAO;
import cz.panjeskyne.model.Race;

@Repository
public class RaceDAOImpl extends AbstractBaseDAO<Race> implements RaceDAO {
	
	public RaceDAOImpl() {
		super(Race.class);
	}
}
