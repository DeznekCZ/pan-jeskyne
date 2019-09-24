package cz.deznekcz.games.panjeskyne.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Kind;
import cz.deznekcz.games.panjeskyne.model.xml.Race;
import cz.deznekcz.games.panjeskyne.module.AModule;
import cz.deznekcz.games.panjeskyne.service.KindService;
import cz.deznekcz.games.panjeskyne.service.RaceService;

public class KindService {

	private Map<String, Kind> kinds;

	private RaceService raceService;

	private AModule module;

	public KindService(AModule module) {
		this.module = module;
		raceService = new RaceService(module, this);
		
		kinds = Maps.newHashMap();
		for (Race race : raceService.getAll()) {
			kinds.putAll(race.getKinds());
		}
	}

	public Kind getByCodename(String codename) {
		return kinds.get(codename);
	}

	public Collection<Kind> getAll() {
		return kinds.values();
	}

	public Kind getCharactersKind(Character character) {
//		return getByCodename(character.getKind());
		return null;
	}

	public Collection<Kind> getKindsForRace(String raceCodename) {
		return raceService.getKindsForRace(raceCodename);
	}
	
	public RaceService getRaceService() {
		return raceService;
	}
	
	public AModule getModule() {
		return module;
	}
}
