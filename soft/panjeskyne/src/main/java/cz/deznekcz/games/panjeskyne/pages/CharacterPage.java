package cz.deznekcz.games.panjeskyne.pages;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import cz.deznekcz.games.panjeskyne.Messages;
import cz.deznekcz.games.panjeskyne.Redirection;
import cz.deznekcz.games.panjeskyne.commands.Commands;
import cz.deznekcz.games.panjeskyne.error.Errors;
import cz.deznekcz.games.panjeskyne.layout.MainBar;
import cz.deznekcz.games.panjeskyne.user.User;
import cz.deznekcz.games.panjeskyne.utils.Out;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("dracidoupe")
@Push
public class CharacterPage extends VerticalLayout implements RootComponent {
	
	private static final long serialVersionUID = 1L;
	
	private User user;

	private TextField characterName;
	
	private static boolean saved;

	@Override
    public void init(VaadinRequest vaadinRequest) {
		user = getSession().getAttribute(User.class);
		saved = true;
		
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
		
		Component mainBar = MainBar.get(this::logout, menuBar, "Tvorba postavy");
		addComponent(mainBar);
		
		MenuItem characters = menuBar.get().addItem("Postavy");
		characters.addItem("Vytvořit...").setCommand(Commands.newCharacter());
		characters.addItem("Zobrazit...").setCommand(Commands.viewCharacter());
		characters.addSeparator();
		MenuItem saveItem = characters.addItem("Uložit");
		saveItem.setCommand(Commands.saveCharacter(this));
		
		characterName = new TextField();
	}

	public boolean logout() {
		if (saved) {
			return true;
		} else {
			return true;
		}
	}

	private String getCharacterName() {
		return characterName.getValue();
	}
	
	@Override
	public void detach() {
		if (!saved) {
			Dialogs.ask(Dialogs.Type.YES_NO_CANCEL)
			.message(Messages.saveCharacter(getCharacterName()))
			.onOk(this::saveCharacterAndClose)
			.onNo(super::detach)
			.show();
		} else {
			super.detach();
		}
	}
	
	public void saveCharacter() {
		saved = true;
	}
	
	private void saveCharacterAndClose() {
		saveCharacter();
		
		if (saved) super.detach();
		else Errors.Notify.characterUnsaved(getCharacterName());
	}
}
