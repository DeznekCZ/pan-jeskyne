package cz.deznekcz.games.panjeskyne.layout;

import java.util.function.Supplier;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import cz.deznekcz.games.panjeskyne.error.Errors;
import cz.deznekcz.games.panjeskyne.utils.Out;

public class MainBar {


	public static Component get(Supplier<Boolean> logoutMethod, Out<MenuBar> out) {
		return get(logoutMethod, out, "Webový simlátor pro nadšence drčího doupěte + v novém kabátku");
	}
	
	public static Component get(Supplier<Boolean> logoutMethod, Out<MenuBar> out, String subName) {
		HorizontalLayout mainBar = new HorizontalLayout();
		mainBar.setWidth(100, Unit.PERCENTAGE);
		mainBar.addStyleName("main-bar");
		
		Label logo = new Label();
		logo.setHeight(86, Unit.PIXELS);
		logo.setValue("Dračí doupě");
		logo.addStyleName(ValoTheme.LABEL_H1);
		
		MenuBar menuBar = new MenuBar();
		menuBar.setWidth(100, Unit.PERCENTAGE);
		mainBar.addComponent(menuBar);
		mainBar.setExpandRatio(menuBar, 1);
		out.set(menuBar);
		
		Button logout = new Button();
		logout.setCaption("Odhlásit se");
		logout.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		logout.addClickListener((event) -> {
			logout(event.getButton().getUI().getSession(), logoutMethod.get());
		});
		mainBar.addComponent(logout);
		
		VerticalLayout v = new VerticalLayout();
		v.setWidth(100, Unit.PERCENTAGE);
		if (subName != null) {
			Label subPart = new Label(subName);
			
			HorizontalLayout h = new HorizontalLayout();
			h.setHeight(86, Unit.PIXELS);
			h.addComponent(logo);
			h.addComponent(subPart);
			h.setComponentAlignment(logo, Alignment.TOP_LEFT);
			h.setComponentAlignment(subPart, Alignment.BOTTOM_LEFT);
			v.addComponent(h);
		} else {
			v.addComponent(logo);
			v.setComponentAlignment(logo, Alignment.TOP_LEFT);
		}
		v.addComponent(mainBar);
		
		return v;
	}

	private static void logout(VaadinSession session, boolean force) {
		if (session.getUIs().size() > 1) {
			Errors.Notify.closeOtherTabs();
			if (force) {
				UI.getCurrent().close();
			}
		} else if (force) {
			session.close();
		}
	}

	public static void forceLogout(VaadinSession session) {
		logout(session, true);
	}
}
