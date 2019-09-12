package cz.deznekcz.games.panjeskyne.i18n;

import cz.deznekcz.games.panjeskyne.service.I18NService;

public interface I18NTexts {

	public static final String NAME = "%s.%s.NAME";
	public static final String DESCRIPTION = "%s.%s.DESC";
	
	public default String getNameI18N(String locale) {
		return I18NService.getString(locale, 
				String.format(NAME, getClassName(), getId()), getNameSafe());
	}
	
	public default String getDescriptionI18N(String locale) {
		return I18NService.getString(locale, 
				String.format(DESCRIPTION, getClassName(), getId()), getDescSafe());
	}
	
	public default String getClassName() {
		return getClass().getSimpleName();
	}
	
	public default String getNameSafe() {
		return getName() != null ? getName() : "";
	}
	
	public default String getDescSafe() {
		return getDesc() != null ? getDesc() : "";
	}

	public String getId();
	
	public String getName();
	
	public String getDesc();
}
