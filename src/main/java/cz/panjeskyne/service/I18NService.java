package cz.panjeskyne.service;

import cz.panjeskyne.service.impl.I18NServiceImpl;

public interface I18NService {

	static String getString(String locale, String codename) {
		return I18NServiceImpl.getString(codename); // musí vracet codename nebo to co tam je
	}

}
