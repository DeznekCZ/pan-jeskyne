package cz.panjeskyne.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import cz.panjeskyne.model.xml.Race;
import cz.panjeskyne.service.RaceService;

@Service
public class RaceServiceImpl implements RaceService {

	private final ImmutableMap<String, Race> races = RaceKindLoader.loadRacesAndKinds();
	
	@Override
	public Race getByCodename(String codename) {
		return races.get(codename);
	}

	@Override
	public Collection<Race> getAll() {
		return races.values();
	}

}
