package cz.deznekcz.games.panjeskyne.pages;

import java.util.function.Supplier;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import cz.deznekcz.games.panjeskyne.Redirection;
import cz.deznekcz.games.panjeskyne.commands.Commands;
import cz.deznekcz.games.panjeskyne.error.Errors;
import cz.deznekcz.games.panjeskyne.layout.MainBar;
import cz.deznekcz.games.panjeskyne.pages.Dialogs.Type;
import cz.deznekcz.games.panjeskyne.user.User;
import cz.deznekcz.games.panjeskyne.utils.Out;


public class MainPage extends VerticalLayout implements ClickListener, RootComponent {
	
	private static final long serialVersionUID = 1L;
	
	private User user;

	@Override
    public void init(VaadinRequest vaadinRequest) {
		user = getSession().getAttribute(User.class);
		
		if (user != null) {
			showPage(user);
		} else {
			Redirection.toLogin();
		}
    }

	private void showPage(User user) {
		setWidth(100, Unit.PERCENTAGE);
		setHeight(100, Unit.PERCENTAGE);
		
		Out<MenuBar> menuBar = Out.init();
		Component mainBar = MainBar.get(this, menuBar);
		addComponent(mainBar);
		
		MenuItem characters = menuBar.get().addItem("Postavy");
		characters.addItem("Vytvořit...").setCommand(Commands.newCharacter());
		characters.addItem("Zobrazit...").setCommand(Commands.viewCharacter());
		
		MenuItem skills = menuBar.get().addItem("Dovednosti");
		skills.addItem("Zobrazit ...").setCommand(Commands.listSkills());
		if (user.isAdmin("skill")) {
			skills.addItem("Vytvořit ...").setCommand(Commands.newSkills());
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		MainBar.logout(event.getButton().getUI().getSession(), false);
	}
}
