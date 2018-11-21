package cz.panjeskyne.service;

import java.util.Collection;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Race;
import cz.panjeskyne.model.xml.Statistic;

public interface RaceService {

	static Number getStatistic(Character character, Statistic statistic) {
		return 0; // TODO
	}

	Race getByCodename(String codename);

	Collection<Race> getAll();

	Race getCharactersRace(Character character);
}
