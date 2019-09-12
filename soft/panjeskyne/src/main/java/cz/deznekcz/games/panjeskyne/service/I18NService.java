package cz.deznekcz.games.panjeskyne.service;

import cz.deznekcz.games.panjeskyne.service.impl.I18NServiceImpl;

public interface I18NService {

	static String getString(String locale, String codename) {
		return I18NServiceImpl.getString(codename); // mus� vracet codename nebo to co tam je
	}

	static String getString(String locale, String codename, String defaultText) {
		return I18NServiceImpl.getString(codename, defaultText); // mus� vracet codename nebo to co tam je
	}

}
