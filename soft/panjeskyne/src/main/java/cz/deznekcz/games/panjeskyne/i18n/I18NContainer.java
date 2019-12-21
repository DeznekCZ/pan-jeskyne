package cz.deznekcz.games.panjeskyne.i18n;

import java.util.List;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import cz.deznekcz.games.i18n.I18NText;
import cz.deznekcz.games.i18n.I18nTextElement;

public interface I18NContainer {

	List<I18NText> getDescriptions();

	List<I18NText> getNames();

	default String getName() {
		return getText(getNames());
	}

	default String getDescription() {
		return getText(getDescriptions());
	}

	static String getText(List<I18NText> list) {
		I18NText text = null;
		
		if (list.size() == 0) {
			return "";
		}
		
		for (I18NText i18nText : list) {
			if (i18nText.getLang().equals(I18N.LOCALE)) {
				text = i18nText;
			}
		}
		
		if (text == null) {
			text = list.get(0);
		}
		
		return text.convert();
	}
	
	static Label toLabel(String text) {
		return new Label(text, ContentMode.HTML);
	}
}
