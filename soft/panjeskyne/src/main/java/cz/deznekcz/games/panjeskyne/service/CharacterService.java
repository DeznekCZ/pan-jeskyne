package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.Lists;

import cz.deznekcz.games.panjeskyne.data.Character;

public class CharacterService {
	
	private static final String WORLD_CHAR_PATH = "/home/data/world/%s/character/";

	public static List<Character> getWorldCharacters(String id) {
		File charactersDirectory = new File(String.format(WORLD_CHAR_PATH, id));
		List<Character> characters = Lists.newArrayList();
		
		File[] charFiles = charactersDirectory.listFiles();
		if (charFiles != null) {
			for (File charFile : charFiles) {
				Character character = getCharacter(id, charFile.getName().replace(".xml", ""));
				characters.add(character);
			}
		}
		
		return characters;
	}

	public static Character getCharacter(String worldId, String characterId) {
		File charXml = new File(String.format(WORLD_CHAR_PATH, worldId) + characterId + ".xml");
		
		try {
			JAXBContext jc = JAXBContext.newInstance(Character.class);
            Unmarshaller u = jc.createUnmarshaller();
            Character character = (Character)u.unmarshal(charXml);
            return character;
		} catch (JAXBException e) {
			Character character = new Character();
			character.init(charXml.getName().replace(".xml", ""));
			character.setError(e);
			return character;
		}
	}
}
