package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

import cz.deznekcz.games.panjeskyne.model.xml.Character;

public class CharacterLoader {

	private static final String CHAR_XML = "/home/data/character/";

	public static Character load(String name) {
		Character character = new Character();
		character.setId(name);
		character.setName(name + "#nenalezen");
		character.setKindCodename(null);
		
		try {
			File file = new File(CHAR_XML + name + ".xml");
			if (!file.exists()) throw new FileNotFoundException(file.getAbsolutePath());
			
			JAXBContext jc = JAXBContext.newInstance(Character.class);
            Unmarshaller u = jc.createUnmarshaller();
            
            character = (Character)u.unmarshal(file);
			
		} catch (JAXBException | IOException e) {
			character.setError(e.getLocalizedMessage() + "\n" + StringUtils.join(e.getStackTrace(), "\n"));
			e.printStackTrace();
		}
		return character;
	}

}
