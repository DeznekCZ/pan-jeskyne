package cz.panjeskyne.service;

import java.util.Collection;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Kind;

public interface KindService {

	Kind getByCodename(String codename);

	Collection<Kind> getAll();
	
	Kind getCharactersKind(Character character);

	Collection<Kind> getKindsForRace(String raceCodename);
}
