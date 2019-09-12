package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.ImmutableMap;

import cz.deznekcz.games.panjeskyne.model.xml.Kind;
import cz.deznekcz.games.panjeskyne.model.xml.Race;
import cz.deznekcz.games.panjeskyne.model.xml.Races;

public class RaceKindLoader {
	
	private static final String RACES_XML = "/home/data/races.xml";

	public static ImmutableMap<String, Race> loadRacesAndKinds() {
		ImmutableMap.Builder<String,Race> builder= ImmutableMap.builder();
		
		try {
			File file = new File(RACES_XML);
			if (!file.exists()) throw new FileNotFoundException(file.getAbsolutePath());
			JAXBContext jc = JAXBContext.newInstance(Races.class);
            Unmarshaller u = jc.createUnmarshaller();
            
			Races stats = (Races)u.unmarshal(file);
			for(Race race : stats.getRaces()) {
				for (Kind kind : race.getKinds().values()) {
					kind.setRace(race);
				}
				builder.put(race.getCodename(), race);
			}
		} catch (JAXBException | IOException e) {
			throw new IllegalStateException("Statistics could not be loaded from the XML.", e);
		}
		
		return builder.build();
	}
}
