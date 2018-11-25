package cz.panjeskyne.service;

import cz.panjeskyne.model.db.Table;
import cz.panjeskyne.service.formula.FormulaException;
import cz.panjeskyne.service.impl.TableServiceImpl;

public interface TableService {

	static Table getTable(String identifier) throws FormulaException {
		return TableServiceImpl.getTable(identifier);
	}

}
