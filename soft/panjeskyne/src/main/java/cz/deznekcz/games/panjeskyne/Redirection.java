package cz.deznekcz.games.panjeskyne;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

import cz.deznekcz.games.panjeskyne.pages.RootComponent;
import cz.deznekcz.games.panjeskyne.pages.CharacterPage;
import cz.deznekcz.games.panjeskyne.pages.LoginPage;
import cz.deznekcz.games.panjeskyne.pages.MainPage;

@Theme("dracidoupe")
@Push
public class Redirection extends UI {

	private static void redirect(String redirection) {
		System.out.println("Redirect to: " + redirection);
		UI.getCurrent().getPage().setLocation(redirection);
	}

	@Override
	protected void init(VaadinRequest request) {
		String path = request.getPathInfo();
		
		if (path.startsWith("/login")) {
			init(request, new LoginPage());
		} else if (path.startsWith("/character")) {
			init(request, new CharacterPage());
		} else {
			init(request, new MainPage());
		}
	}

	private void init(VaadinRequest request, RootComponent page) {
		setContent(page);
		page.init(request);
	}

	public static void toLogin() {
		redirect("/login");
	}

	public static void toMain() {
		redirect("/");
	}

	public static void newCharacter(String name) {
		newPage("/character/view=" + name);
	}

	public static void newCharacterCreation() {
		newPage("/character/create");
	}

	private static void newPage(String page) {
		System.out.println("Open: " + page);
		UI.getCurrent().getPage().open(page, "_new");
	}

}
