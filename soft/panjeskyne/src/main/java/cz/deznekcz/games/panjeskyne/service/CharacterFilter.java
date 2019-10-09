package cz.deznekcz.games.panjeskyne.service;

import java.util.function.Predicate;

import cz.deznekcz.games.panjeskyne.data.Character;

public interface CharacterFilter extends Predicate<Character> {
	CharacterFilter acceptAll = c -> true;

	boolean filter(Character character);
	
	@Override
	default boolean test(Character character) {
		return filter(character);
	}
}
