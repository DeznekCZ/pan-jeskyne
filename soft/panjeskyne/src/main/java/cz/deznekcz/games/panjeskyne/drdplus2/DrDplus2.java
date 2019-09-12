package cz.deznekcz.games.panjeskyne.drdplus2;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.module.AModule;
import cz.deznekcz.games.panjeskyne.module.ModuleException;
import cz.deznekcz.games.panjeskyne.user.User;

public class DrDplus2 extends AModule {

	private static final String MODULE_KEY = "drd+2";

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
					portraitLayout.addComponent(portrait);
					portraitLayout.setHeight("200px");
					portraitLayout.setWidth("200px");
				}
				
				{ VerticalLayout mainStats = new VerticalLayout();
					mainStats.addComponent(getStatisticAsField("statistic.sil", character, false, null));
					mainStats.addComponent(getStatisticAsField("statistic.obr", character, false, null));
					mainStats.addComponent(getStatisticAsField("statistic.zrc", character, false, null));
					mainStats.addComponent(getStatisticAsField("statistic.vol", character, false, null));
					mainStats.addComponent(getStatisticAsField("statistic.int", character, false, null));
					mainStats.addComponent(getStatisticAsField("statistic.chr", character, false, null));
					
					portraitLayout.addComponent(mainStats);
					mainStats.setHeightUndefined();
				}
				
				mainDiv.addComponent(portraitLayout);
				portraitLayout.setWidth("200px");
			}
		}
		return w;
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

}
