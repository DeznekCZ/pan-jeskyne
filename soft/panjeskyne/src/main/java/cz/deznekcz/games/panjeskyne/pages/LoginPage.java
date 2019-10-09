package cz.deznekcz.games.panjeskyne.pages;

import java.io.IOException;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import cz.deznekcz.games.panjeskyne.Redirection;
import cz.deznekcz.games.panjeskyne.error.Errors;
import cz.deznekcz.games.panjeskyne.model.enums.Gender;
import cz.deznekcz.games.panjeskyne.user.User;
import cz.deznekcz.serialized.ObjectReader;
import cz.deznekcz.serialized.ObjectWriter;
import cz.deznekcz.serialized.Serialized;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
public class LoginPage extends VerticalLayout implements ClickListener, Serialized, RootComponent {

	private static final long serialVersionUID = 1L;
	
	private TextField username;
	private PasswordField password;
	private Button login;
	private Label error;

	@Override
	public void init(VaadinRequest vaadinRequest) {
		User user = getSession().getAttribute(User.class);
		if (user == null) {
			showPage();
		} else {
			Redirection.toMain();
		}
    }

	private void showPage() {
		FormLayout form = new FormLayout();
		form.setWidth(300, Unit.PIXELS);
		form.setHeight(300, Unit.PIXELS);
		
		username = new TextField();
		username.setId("username");
		username.setCaption("Uživatel");
		
		password = new PasswordField();
		password.setId("password");
		password.setCaption("Heslo");
		
		login = new Button();
		login.setId("button_login");
		login.setCaption("Přihlásit");
		login.addStyleName(ValoTheme.BUTTON_PRIMARY);
		login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		login.addClickListener(this);
		
		error = new Label();
		error.addStyleName("error");
		
		form.addComponents(username, password, login, error);
		
		Label logo = new Label();
		logo.setValue("Dračí doupě");
		logo.addStyleName(ValoTheme.LABEL_H1);
		logo.setHeight(68, Unit.PIXELS);
	
		addComponents(logo, form);
		setHeight(100, Unit.PERCENTAGE);
		setComponentAlignment(logo, Alignment.BOTTOM_CENTER);
		setExpandRatio(logo, 1);
		setComponentAlignment(form, Alignment.TOP_CENTER);
		setExpandRatio(form, 2);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		String username = this.username.getValue();
		String password = this.password.getValue();

		this.username.setComponentError(null);
		this.password.setComponentError(null);
		this.error.setComponentError(null);
		
		if (username == null || username.length() < 3) {
			setError(this.username, Errors.lenghtMinimum(Gender.UNSPECIFIED, "Jméno", 2));
			return;
		}
		
		if (username.length() > 16) {
			setError(this.username, Errors.lenghtMaximum(Gender.UNSPECIFIED, "Jméno", 16));
			return;
		}
		
		User user = User.parse(username);
		if (user.exists()) {
			if (user.matchPassword(password)) {
				getSession().setAttribute(User.class, user);
				Redirection.toMain();
				return;
			} else {
				setError(this.password, Errors.wrongPassword());
				return;
			}
		} else {
			setError(this.username, Errors.missingUser(username));
			return;
		}
	}

	private void setError(AbstractComponent component, ErrorMessage message) {
		component.setComponentError(message);
		error.setComponentError(message);
		error.setValue(message.getFormattedHtmlMessage());
	}

	@Override
	public void store(ObjectWriter writer) throws IOException {
		writer.add(username.getValue());
	}

	@Override
	public void load(ObjectReader reader) throws IOException, ClassNotFoundException {
		showPage();
		
		username.setValue(reader.read());
	}
}
