package cz.deznekcz.games.panjeskyne;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import cz.deznekcz.games.panjeskyne.dialog.LoginDialog;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("dracidoupe")
public class Page extends UI {

	@Override
    protected void init(VaadinRequest vaadinRequest) {
		
		new LoginDialog(this);
    }

    @WebServlet(urlPatterns = "/*", name = "PageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Page.class, productionMode = false)
    public static class PageServlet extends VaadinServlet {
		
    }
}
