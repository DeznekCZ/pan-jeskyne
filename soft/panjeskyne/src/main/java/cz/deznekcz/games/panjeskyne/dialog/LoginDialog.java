package cz.deznekcz.games.panjeskyne.dialog;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.deznekcz.games.panjeskyne.pages.MainPage;
import cz.deznekcz.games.panjeskyne.user.Login;
import cz.deznekcz.games.panjeskyne.user.User;

public class LoginDialog {
	
	private static final String USERS = "/home/users/";

	private TextField mail;
	private Button button;
	private PasswordField pass;

	public LoginDialog(UI ui) {
		Login login = ui.getSession().getAttribute(Login.class);
		if (login == null) {
			
			mail = new TextField();
			mail.setId("username");
			mail.setCaption("Zadej své přihlašovací jméno");
			mail.addValueChangeListener(change -> {
				String text = change.getValue();
				
				if (StringUtils.isEmpty(text)) {
					mail.setComponentError(LoginError.LOGIN_EMPTY);
					button.setEnabled(false);
				} else {
					File f = new File(USERS + text);
					if (!f.exists()) {
						mail.setComponentError(LoginError.LOGIN_BAD_USER);
						button.setEnabled(false);
					} else {
						mail.setComponentError(null);
						button.setEnabled(true);
					}
				}
			});
			
			pass = new PasswordField();
			pass.setCaption("Zadej své heslo:");
			pass.setId("password");
			
			button = new Button();
			button.setId("submit");
			button.setCaption("Přihlásit");
			button.addClickListener(event -> {
				User user = User.parse(USERS + mail.getValue());
				if (user.matchPassword(pass.getValue())) {
					ui.getSession().setAttribute(Login.class, new Login(user));
					button.setComponentError(null);
					ui.setContent(new MainPage(ui.getSession().getAttribute(Login.class)));
				} else {
					button.setComponentError(LoginError.PASSWORD);
				}
			});
			
			VerticalLayout layout = new VerticalLayout(mail, pass, button);
			layout.setComponentAlignment(mail, Alignment.MIDDLE_CENTER);
			layout.setComponentAlignment(pass, Alignment.MIDDLE_CENTER); 
			layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
			ui.setContent(layout);
		} else {
			ui.setContent(new MainPage(login));
		}
	}

	private <T extends TextField> T onEnter(T passwordField) {
		return null;
	}
}
