package cz.deznekcz.games.panjeskyne.dialog;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.deznekcz.games.panjeskyne.pages.MainPage;
import cz.deznekcz.games.panjeskyne.user.Login;
import cz.deznekcz.games.panjeskyne.user.User;

public class LoginDialog {
	
	private TextField username;
	private Button button;
	private PasswordField pass;

	public LoginDialog(UI ui) {
		Login login = ui.getSession().getAttribute(Login.class);
		if (login == null) {
			
			username = new TextField();
			username.setId("username");
			username.setCaption("Zadej své přihlašovací jméno");
			username.addValueChangeListener(change -> {
				String text = change.getValue();
				
				if (StringUtils.isEmpty(text)) {
					username.setComponentError(LoginError.LOGIN_EMPTY);
					button.setEnabled(false);
				} else {
					File f = new File(User.USERS + text);
					if (!f.exists()) {
						username.setComponentError(LoginError.LOGIN_BAD_USER);
						button.setEnabled(false);
					} else {
						username.setComponentError(null);
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
				User user = User.parse(username.getValue());
				if (user.matchPassword(pass.getValue())) {
					ui.getSession().setAttribute(Login.class, new Login(user));
					button.setComponentError(null);
					ui.setContent(new MainPage(ui.getSession().getAttribute(Login.class)));
				} else {
					button.setComponentError(LoginError.PASSWORD);
				}
			});
			
			VerticalLayout layout = new VerticalLayout(username, pass, button);
			layout.setComponentAlignment(username, Alignment.MIDDLE_CENTER);
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
