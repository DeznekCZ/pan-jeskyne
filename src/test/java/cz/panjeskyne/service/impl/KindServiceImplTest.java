package cz.panjeskyne.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.panjeskyne.model.xml.Kind;
import cz.panjeskyne.test.AbstractSpringTest;

public class KindServiceImplTest extends AbstractSpringTest {
	
	@Autowired
	private KindServiceImpl service;
	
	@Test
	public void getByCodeName() {
		Kind kind = service.getByCodename("man");
		assertEquals(kind.getId(), "man");
	}
}
