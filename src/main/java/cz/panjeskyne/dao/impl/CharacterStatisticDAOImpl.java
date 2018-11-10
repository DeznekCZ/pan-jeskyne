package cz.panjeskyne.dao.impl;

import org.springframework.stereotype.Repository;

import cz.panjeskyne.dao.AbstractBaseDAO;
import cz.panjeskyne.dao.CharacterStatisticDAO;
import cz.panjeskyne.model.CharacterStatistic;

@Repository
public class CharacterStatisticDAOImpl extends AbstractBaseDAO<CharacterStatistic> implements CharacterStatisticDAO {
	
	public CharacterStatisticDAOImpl() {
		super(CharacterStatistic.class);
	}
}
