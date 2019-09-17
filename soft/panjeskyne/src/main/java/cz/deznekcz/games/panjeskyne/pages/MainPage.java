package cz.deznekcz.games.panjeskyne.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;

import cz.deznekcz.games.panjeskyne.drdplus2.DrDplus2;
import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.service.CharacterLoader;
import cz.deznekcz.games.panjeskyne.user.Login;

public class MainPage extends VerticalLayout {
	
	private static final String CHARACTER_LINKS = "/home/character.users/";
	
	private Login login;
	private TabSheet sheet;

	private Grid<Character> characterGrid;
	private List<Character> characterList;
	private HorizontalLayout horizontal;

	private DrDplus2 module;


	public MainPage(Login login) {
		this.module = DrDplus2.getModule();
		
		this.characterList = new ArrayList<>(5);
		this.login = login;
		sheet = new TabSheet();
		
		initCharacters();
		
		horizontal = new HorizontalLayout(sheet);
		addComponent(horizontal);
		
		int i = 0;
		while(i++ == 10000);
	}

	private void initCharacters() {
		characterGrid = new Grid<>();
		characterGrid.addColumn(Character::getName).setCaption("Jméno Postavy");
		characterGrid.addColumn(ch -> "Otevřít",
			      new ButtonRenderer<>(clickEvent -> {
			    	  getUI().addWindow(module.getCharacterPreviewScreen(login.getUser(), 
			    			  CharacterLoader.load(clickEvent.getItem().getId())));
			    }));
		
		Button create = new Button();
		create.setCaption("Vytvořit postavu");
		create.addClickListener(event -> {
			getUI().addWindow(module.getCharacterCreationScreen(this::refreshList));
		});
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.addComponent(create);
		verticalLayout.addComponent(characterGrid);
		verticalLayout.setExpandRatio(characterGrid, 1);
		
		sheet.addTab(verticalLayout, "Postavy");
		
		refreshList();
	}

	private void refreshList() {
		File characterLinks = new File(CHARACTER_LINKS + login.getUser().getUserName());
		File[] characters = characterLinks.listFiles();
		if (characters == null || characters.length == 0) {
			characterList.clear();
		} else {
			characterList.clear();
			for (File file : characters) {
				characterList.add(CharacterLoader.load(file.getName()));
			}
		}
		
		characterGrid.setItems(characterList);
	}

}
