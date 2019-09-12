package cz.deznekcz.games.panjeskyne;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import cz.deznekcz.games.panjeskyne.dialog.LoginDialog;
import cz.deznekcz.games.panjeskyne.service.KindService;
import cz.deznekcz.games.panjeskyne.service.SkillService;
import cz.deznekcz.games.panjeskyne.service.StatisticService;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("dracidoupe")
public class Page extends UI {

//    private ComboBox<Kind> kindBox;
//	private ComboBox<Race> raceBox;
//	
//	private List<ComponentPair<Statistic, Label>> statistics;
//	
//	private Character character;

	@Override
    protected void init(VaadinRequest vaadinRequest) {
//		character = new Character();
//		
//        final VerticalLayout layout = new VerticalLayout();
//        
//        raceBox = new ComboBox<>();
//        raceBox.setItems(PageServlet.getInstance().getKindService().getRaceService().getAll());
//        raceBox.setItemCaptionGenerator(Race::getName);
//        raceBox.addValueChangeListener(raceEvent -> {
//        	if (raceEvent.getValue() == null) {
//        		kindBox.clear();
//        		kindBox.setItems();
//        	} else {
//        		kindBox.clear();
//        		kindBox.setItems(raceEvent.getValue().getKinds().values());
//        	}
//        });
//
//        kindBox = new ComboBox<>();
//        kindBox.setItemCaptionGenerator(Kind::getName);
//        kindBox.addValueChangeListener(kindEvent -> {
//        	StatisticService service = PageServlet.getInstance().getStatisticService();
//        	character.setKind(kindEvent.getValue());
//        	
//        	for (ComponentPair<Statistic, Label> pair : statistics) {
//        		Result result = service.getValue(character, pair.getData());
//        		if (result.isSuccessful()) {
//        			pair.getComponent().setCaption(Double.toString(Math.ceil(result.getValue())));
//        		} else {
//        			pair.getComponent().setCaption(result.getException().getMessage());
//        		}
//			}
//        });
//
//        layout.addComponent(raceBox);
//        layout.addComponent(kindBox);
//        
//        statistics = new ArrayList<ComponentPair<Statistic,Label>>();
//        
//        for (Statistic statistic : PageServlet.getInstance().getStatisticService().getStatisticsByGroup("main")) {
//			Label caption = new Label(statistic.getName());
//			Label value   = new Label();
//			
//			HorizontalLayout statsLayout = new HorizontalLayout(caption, value);
//			layout.addComponent(statsLayout);
//			
//			statistics.add(new ComponentPair<>(statistic, value));
//		}
//        
//        { // zdravi
//        	Statistic statistic = PageServlet.getInstance().getStatisticService().getByCodename("statistic.zdr");
//        	
//        	Label caption = new Label(statistic.getName());
//			Label value   = new Label();
//			
//			HorizontalLayout statsLayout = new HorizontalLayout(caption, value);
//			layout.addComponent(statsLayout);
//			
//			statistics.add(new ComponentPair<>(statistic, value));
//        }
//        
//        { // zivoty
//        	Statistic statistic = PageServlet.getInstance().getStatisticService().getByCodename("character.zivoty");
//        	
//        	Label caption = new Label(statistic.getName());
//			Label value   = new Label();
//			
//			HorizontalLayout statsLayout = new HorizontalLayout(caption, value);
//			layout.addComponent(statsLayout);
//			
//			statistics.add(new ComponentPair<>(statistic, value));
//        }
//        
//        setContent(layout);
		
		new LoginDialog(this);
    }

    @WebServlet(urlPatterns = "/*", name = "PageServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Page.class, productionMode = false)
    public static class PageServlet extends VaadinServlet {
		
    }
}
