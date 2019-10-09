package cz.deznekcz.games.i18n;

import java.util.Locale;

import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;

public interface Localized extends Component {
	default void setLocale(Locale locale) {
		updateMessageStrings(this);
	}
	
	default void updateMessageStrings(Component component) {
		if (component instanceof Localized) {
			((Localized) component).updateMessageStrings();
		}
		if (component instanceof HasComponents) {
			((HasComponents) component).iterator().forEachRemaining(this::updateMessageStrings);
		}
	}

	void updateMessageStrings();
}
