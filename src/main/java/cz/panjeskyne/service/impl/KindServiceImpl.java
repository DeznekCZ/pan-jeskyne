package cz.panjeskyne.service.impl;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Kind;
import cz.panjeskyne.model.xml.Race;
import cz.panjeskyne.service.KindService;
import cz.panjeskyne.service.RaceService;

@Service
public class KindServiceImpl implements KindService {

	private ImmutableMap<String, Kind> kinds;

	@Autowired
	private RaceService raceService;

	@PostConstruct
	private void init() {
		ImmutableMap.Builder<String, Kind> builder = ImmutableMap.<String, Kind>builder();
		for (Race race : raceService.getAll()) {
			builder.putAll(race.getKinds());
		}
		kinds = builder.build();
	}

	@Override
	public Kind getByCodename(String codename) {
		return kinds.get(codename);
	}

	@Override
	public Collection<Kind> getAll() {
		return kinds.values();
	}

	@Override
	public Kind getCharactersKind(Character character) {
		return getByCodename(character.getKindCodename());
	}
}
