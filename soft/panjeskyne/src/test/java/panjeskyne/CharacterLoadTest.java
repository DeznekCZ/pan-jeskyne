package panjeskyne;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.service.CharacterService;

class CharacterLoadTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		Character character = CharacterService.getInstance().getCharacter("borag");
		
		assertNull(character.getError());
		
		assertEquals("Borag", character.getName().trim(), "Name");
	}

}
