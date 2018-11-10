package cz.panjeskyne.dao.impl;

import org.springframework.stereotype.Repository;

import cz.panjeskyne.dao.AbstractBaseDAO;
import cz.panjeskyne.dao.CharacterDAO;
import cz.panjeskyne.model.Character;

@Repository
public class CharacterDAOImpl extends AbstractBaseDAO<Character> implements CharacterDAO {
	
	public CharacterDAOImpl() {
		super(Character.class);
	}
}
