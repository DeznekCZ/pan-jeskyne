package cz.panjeskyne.service;

import java.util.Collection;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Kind;
import cz.panjeskyne.model.xml.Statistic;

public interface KindService {

	static double getStatistic(Character character, Statistic statistic) {
		return 0.0; // TODO
	}

	Kind getByCodename(String codename);

	Collection<Kind> getAll();

}
