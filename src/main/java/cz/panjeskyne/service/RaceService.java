package cz.panjeskyne.service;

import cz.panjeskyne.model.Character;
import cz.panjeskyne.model.Race;
import cz.panjeskyne.model.Statistic;

public interface RaceService extends BaseService<Race>{

	static Number getStatistic(Character character, Statistic statistic) {
		return 0; // TODO
	}

}
