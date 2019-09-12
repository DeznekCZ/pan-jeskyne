package cz.deznekcz.games.panjeskyne.service;

import java.util.Collection;

import com.google.common.collect.ImmutableMap;

import cz.deznekcz.games.panjeskyne.client.Services;
import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Kind;
import cz.deznekcz.games.panjeskyne.model.xml.Race;
import cz.deznekcz.games.panjeskyne.service.RaceService;

public class RaceService {

	private final ImmutableMap<String, Race> races = RaceKindLoader.loadRacesAndKinds();
	
	public RaceService() {
		
	}

	public Race getByCodename(String codename) {
		return races.get(codename);
	}

	public Collection<Race> getAll() {
		return races.values();
	}

	public Race getCharactersRace(Character character) {
		Kind kind = Services.getKindService().getByCodename(character.getKindCodename());
		return kind.getRace();
	}

}
