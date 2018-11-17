package cz.panjeskyne.dao.impl;

import org.springframework.stereotype.Repository;

import cz.panjeskyne.dao.AbstractBaseDAO;
import cz.panjeskyne.dao.KindDAO;
import cz.panjeskyne.model.Kind;

@Repository
public class KindDAOImpl extends AbstractBaseDAO<Kind> implements KindDAO {
	
	public KindDAOImpl() {
		super(Kind.class);
	}
}
