package cz.panjeskyne.service.impl;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import cz.panjeskyne.model.xml.Race;

public class RaceKindLoaderTest {

	@Test
	public void load() {
		ImmutableMap<String, Race> races = RaceKindLoader.loadRacesAndKinds();
		Race human = races.get("human");
		assertThat(human, notNullValue());
		assertThat(human.getKinds().size(), greaterThan(0));
	}
}
