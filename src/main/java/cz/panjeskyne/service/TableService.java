package cz.panjeskyne.service;

import cz.panjeskyne.model.db.Table;
import cz.panjeskyne.service.impl.TableServiceImpl;

public interface TableService {

	static Table getTable(String identifier) {
		return TableServiceImpl.getTable(identifier);
	}

}
