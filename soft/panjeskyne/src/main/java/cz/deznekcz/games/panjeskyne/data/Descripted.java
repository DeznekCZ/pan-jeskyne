package cz.deznekcz.games.panjeskyne.data;

import java.util.Map;

import cz.deznekcz.games.panjeskyne.i18n.I18N;

public interface Descripted {
	
	public default String getDescription() {
		Map<String, LangData> data = getDescriptionMap();
		
		if (data == null || data.size() == 0) {
			return I18N.DATA_NOT_FOUND.getString(getId() + ".description");
		} else {
			if (data.containsKey(I18N.LOCALE)) {
				return data.get(I18N.LOCALE).getStringValue();
			} else if (data.containsKey("enUS")) {
				return data.get("enUS").getStringValue();
			} else if (data.containsKey("csCZ")) {
				return data.get("csCZ").getStringValue();
			}
			
			return I18N.DATA_NOT_FOUND.getString(getId() + ".description");
		}
	}

	public String getId();

	public Map<String, LangData> getDescriptionMap();
}
