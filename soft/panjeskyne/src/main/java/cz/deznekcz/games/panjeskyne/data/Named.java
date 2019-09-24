package cz.deznekcz.games.panjeskyne.data;

import java.util.Map;

import cz.deznekcz.games.panjeskyne.i18n.I18N;

public interface Named {

	public default String getName() {
		Map<String, LangData> data = getNameMap();
		
		if (data == null || data.size() == 0) {
			return I18N.DATA_NOT_FOUND.getString(getId() + ".name");
		} else {
			if (data.containsKey(I18N.LOCALE)) {
				return data.get(I18N.LOCALE).getStringValue();
			} else if (data.containsKey("enUS")) {
				return data.get("enUS").getStringValue();
			} else if (data.containsKey("csCZ")) {
				return data.get("csCZ").getStringValue();
			}
			
			return I18N.DATA_NOT_FOUND.getString(getId() + ".name");
		}
	}

	public String getId();

	public Map<String, LangData> getNameMap();
}
