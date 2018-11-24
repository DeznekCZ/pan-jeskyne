package cz.panjeskyne.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Kind;
import cz.panjeskyne.model.xml.Race;
import cz.panjeskyne.service.KindService;
import cz.panjeskyne.service.RaceService;

@Service
public class RaceServiceImpl implements RaceService {

	private final ImmutableMap<String, Race> races = RaceKindLoader.loadRacesAndKinds();
	
	@Autowired
	private KindService kindService;

	@Override
	public Race getByCodename(String codename) {
		return races.get(codename);
	}

	@Override
	public Collection<Race> getAll() {
		return races.values();
	}

	@Override
	public Race getCharactersRace(Character character) {
		Kind kind = kindService.getByCodename(character.getKindCodename());
		return kind.getRace();
	}

}
