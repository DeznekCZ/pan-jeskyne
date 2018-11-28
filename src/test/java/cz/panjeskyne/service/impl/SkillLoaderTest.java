package cz.panjeskyne.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.panjeskyne.test.AbstractSpringTest;

public class SkillLoaderTest extends AbstractSpringTest {
	
	@Autowired
	private SkillServiceImpl skillService;
	
	@Autowired
	private SkillGroupServiceImpl groupService;
	
	@Test
	public void loadTest() throws Exception {
		System.out.println(skillService.getAll());
		System.out.println(groupService.getAll());

		assertEquals("Zázemí", skillService.getByCodename("zazemi.type").getNameI18N(""));
		assertEquals("Dovednosti postavy", skillService.getByCodename("zazemi.skills").getNameI18N(""));
	}
}
