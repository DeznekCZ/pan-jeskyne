package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.Maps;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Kind;
import cz.deznekcz.games.panjeskyne.model.xml.Race;
import cz.deznekcz.games.panjeskyne.model.xml.Races;
import cz.deznekcz.games.panjeskyne.module.AModule;
import cz.deznekcz.games.panjeskyne.service.RaceService;

public class RaceService {
	
	private static final String RACES_XML = "/home/data/%s/races.xml";

	private static RaceService instance;
	
	private final Map<String, Race> races;

	private KindService kindService;

	private AModule module;
	
	public RaceService(AModule module, KindService kindService) {
		races = Maps.newHashMap();
		this.kindService = kindService;
		this.module = module;
		
		try {
			File file = new File(String.format(RACES_XML, module.getId()));
			if (!file.exists()) throw new FileNotFoundException(file.getAbsolutePath());
			JAXBContext jc = JAXBContext.newInstance(Races.class);
            Unmarshaller u = jc.createUnmarshaller();
            
			Races stats = (Races)u.unmarshal(file);
			for(Race race : stats.getRaces()) {
				for (Kind kind : race.getKinds().values()) {
					kind.setRace(race);
				}
				races.put(race.getCodename(), race);
			}
		} catch (JAXBException | IOException e) {
			throw new IllegalStateException("Statistics could not be loaded from the XML.", e);
		}
	}

	public RaceService() {
		// TODO Auto-generated constructor stub
		races = null;
	}

	public Race getByCodename(String codename) {
		return races.get(codename);
	}

	public Collection<Race> getAll() {
		return races.values();
	}

	public Race getCharacterRace(Character character) {
//		Kind kind = kindService.getByCodename(character.getKind());
//		return kind.getRace();
		return null;
	}

	public Collection<Kind> getKindsForRace(String raceCodename) {
		Race race = getByCodename(raceCodename);
		if (race == null) {
			return new ArrayList<>();
		}
		return race.getKinds().values();
	}

	public AModule getModule() {
		return module;
	}

	public static RaceService getInstance() {
		if (instance == null) {
			instance = new RaceService();
		}
		return instance;
	}
}
