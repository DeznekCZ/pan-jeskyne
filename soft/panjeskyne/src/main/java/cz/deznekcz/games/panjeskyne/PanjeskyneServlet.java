package cz.deznekcz.games.panjeskyne;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

import cz.deznekcz.games.events.Events;
import cz.deznekcz.games.panjeskyne.service.CharacterService;
import cz.deznekcz.games.panjeskyne.service.KindService;
import cz.deznekcz.games.panjeskyne.service.RaceService;
import cz.deznekcz.games.panjeskyne.service.SkillService;
import cz.deznekcz.games.panjeskyne.service.StatisticService;

/**
 * Servlet implementation class MainPageServlet
 */
@WebServlet(urlPatterns = "/*", name = "Servlet", asyncSupported = true)
@VaadinServletConfiguration(ui = Redirection.class, productionMode = false)
public class PanjeskyneServlet extends VaadinServlet {
	private static final long serialVersionUID = 1L;
    
    public PanjeskyneServlet() {
    	StatisticService.getInstance();
    	CharacterService.getInstance();
    	SkillService.getInstance();
    	RaceService.getInstance();
    	KindService.getInstance();
    	Events.getInstance();
    }
}
