package cz.deznekcz.games.panjeskyne.drdplus2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Consumer;

import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.module.AModule;
import cz.deznekcz.games.panjeskyne.module.ModuleException;
import cz.deznekcz.games.panjeskyne.user.User;

public class DrDplus2 extends AModule {

	private static final String MODULE_KEY = "drdplus2";
	private static final String PANEL_WIDTH = "300px";
	private static final String FILL_WIDTH = "100%";

	protected DrDplus2() {
		super(MODULE_KEY, "Dačí doupě + 2.0");
	}

	@Override
	public Window getCharacterCreationScreen(Runnable onCreated) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Window getCharacterPreviewScreen(User viewingUser, Character character) {
		Window w = new Window("Postava: " + character.getName());
		
		{ HorizontalLayout mainDiv = new HorizontalLayout();
			{ VerticalLayout portraitLayout = new VerticalLayout();
				{ Image portrait = new Image("");
					portrait.setHeight(PANEL_WIDTH);
					portrait.setWidth(PANEL_WIDTH);
					portraitLayout.addComponent(portrait);
				}
				
				{ VerticalLayout mainStats = new VerticalLayout();
					Consumer<TextField> onAdd = with -> {
						with.setWidth(FILL_WIDTH);
					};
					add(mainStats, getStatisticAsField("statistic.sil", character, false, null), onAdd);
					add(mainStats, getStatisticAsField("statistic.obr", character, false, null), onAdd);
					add(mainStats, getStatisticAsField("statistic.zrc", character, false, null), onAdd);
					add(mainStats, getStatisticAsField("statistic.vol", character, false, null), onAdd);
					add(mainStats, getStatisticAsField("statistic.int", character, false, null), onAdd);
					add(mainStats, getStatisticAsField("statistic.chr", character, false, null), onAdd);
					
					mainStats.setHeightUndefined();
					mainStats.setMargin(false);
					portraitLayout.addComponent(mainStats);
				}
				
				mainDiv.addComponent(portraitLayout);
				portraitLayout.setWidth(PANEL_WIDTH);
			}
			
			mainDiv.setMargin(false);
			w.setContent(mainDiv);
		}
		return w;
	}

	private <L extends ComponentContainer, C extends Component> void add(L l, C c, Consumer<C> onAddAction) {
		l.addComponent(c);
		onAddAction.accept(c);
	}

	@Override
	public Window getCharacterEditScreen(Runnable onSaved, Character character) {
		// TODO Auto-generated method stub
		return null;
	}

	public static DrDplus2 getModule() {
		if (!getModules().containsKey(MODULE_KEY))
		{
			return new DrDplus2();
		} else {
			try {
				return getModule(MODULE_KEY);
			} catch (ModuleException e) {
				return new DrDplus2();
			}
		}
	}

	@Override
	protected void storeModuleToCache(ObjectOutputStream out) {
		
	}

	@Override
	protected void readModuleFromCache(ObjectInputStream in) {
		
	}

}
