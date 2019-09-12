package cz.deznekcz.games.panjeskyne.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import com.google.common.collect.ImmutableMap;

import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Kind;
import cz.deznekcz.games.panjeskyne.model.xml.Race;
import cz.deznekcz.games.panjeskyne.service.KindService;
import cz.deznekcz.games.panjeskyne.service.RaceService;

public class KindService {

	private ImmutableMap<String, Kind> kinds;

	private RaceService raceService;

	public KindService() {
		raceService = new RaceService();
		
		ImmutableMap.Builder<String, Kind> builder = ImmutableMap.<String, Kind>builder();
		for (Race race : raceService.getAll()) {
			builder.putAll(race.getKinds());
		}
		kinds = builder.build();
	}

	public Kind getByCodename(String codename) {
		return kinds.get(codename);
	}

	public Collection<Kind> getAll() {
		return kinds.values();
	}

	public Kind getCharactersKind(Character character) {
		return getByCodename(character.getKindCodename());
	}

	public Collection<Kind> getKindsForRace(String raceCodename) {
		Race race = raceService.getByCodename(raceCodename);
		if (race == null) {
			return new ArrayList<>();
		}
		return race.getKinds().values();
	}
	
	public RaceService getRaceService() {
		return raceService;
	}
}
