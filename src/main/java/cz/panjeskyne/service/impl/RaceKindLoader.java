package cz.panjeskyne.service.impl;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.util.ResourceUtils;

import com.google.common.collect.ImmutableMap;

import cz.panjeskyne.model.xml.Race;
import cz.panjeskyne.model.xml.Races;

public class RaceKindLoader {
	
	public static ImmutableMap<String, Race> loadRacesAndKinds() {
		ImmutableMap.Builder<String,Race> builder= ImmutableMap.builder();
		
		try {
			File file = ResourceUtils.getFile("classpath:moduleData/races.xml");
			JAXBContext jc = JAXBContext.newInstance(Races.class);
            Unmarshaller u = jc.createUnmarshaller();
            
			Races stats = (Races)u.unmarshal(file);
			for(Race race : stats.getRaces()) {
				builder.put(race.getCodename(), race);
			}
		} catch (JAXBException | IOException e) {
			throw new IllegalStateException("Statistics could not be loaded from the XML.", e);
		}
		
		return builder.build();
	}
}
