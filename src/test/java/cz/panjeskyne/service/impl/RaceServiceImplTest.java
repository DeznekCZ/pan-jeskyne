package cz.panjeskyne.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.panjeskyne.model.xml.Race;
import cz.panjeskyne.test.AbstractSpringTest;

public class RaceServiceImplTest extends AbstractSpringTest {
	
	@Autowired
	private RaceServiceImpl service;
	
	@Test
	public void getByCodeName() {
		Race race = service.getByCodename("human");
		assertEquals(race.getId(), "human");
	}
}
