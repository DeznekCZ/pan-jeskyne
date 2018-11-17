package cz.panjeskyne.service;

import cz.panjeskyne.model.Character;
import cz.panjeskyne.model.Kind;
import cz.panjeskyne.model.Statistic;

public interface KindService extends BaseService<Kind>{

	static double getStatistic(Character character, Statistic statistic) {
		return 0.0; // TODO
	}

}
