package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.Lists;

import cz.deznekcz.games.panjeskyne.commands.ViewCharacterCommand;
import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.data.WorldData;

public class CharacterService {
	
	private static final String WORLD_CHAR_PATH = "/home/data/character/";
	private static CharacterService instance;

	public Character getCharacter(String characterId) {
		return characters.getOrDefault(characterId, null);
	}
	
	private static  Character getCharacter(File charXml) {
		try {
			JAXBContext jc = JAXBContext.newInstance(Character.class);
            Unmarshaller u = jc.createUnmarshaller();
            Character character = (Character)u.unmarshal(charXml);
            return character;
		} catch (Exception e) {
			Character character = new Character();
			character.init(charXml.getName().replace(".character.xml", ""));
			character.setError(e);
			return character;
		}
	}

	private HashMap<String, Character> characters;
	
	public CharacterService() {
		characters = new HashMap<>();
	}
	
	private static boolean characterFilter(File f) {
		return f.isFile() && f.getName().endsWith(".character.xml");
	}

	public static CharacterService getInstance() {
		if (instance == null) {
			instance = new CharacterService();
			
			File[] charFiles = new File(WORLD_CHAR_PATH).listFiles(CharacterService::characterFilter);
			if (charFiles != null) {
				for (File charXml : charFiles) {
					Character ch = getCharacter(charXml);
					if (ch.getError() != null) {
						System.err.println("Error while loading of character: " + ch.getId());
						ch.getError().printStackTrace(System.err);
					} else {
						instance.characters.put(ch.getId(), ch);
					}
				}
			}
		}
		return instance;
	}

	public List<Character> getAll() {
		return getAll(CharacterFilter.acceptAll);
	}

	public List<Character> getAll(CharacterFilter character) {
		List<Character> characters = new ArrayList<>(); 
		
		this.characters.values().stream().filter(character).forEach(characters::add);
		
		return characters;
	}
}
