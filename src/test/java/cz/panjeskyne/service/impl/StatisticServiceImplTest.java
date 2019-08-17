package cz.panjeskyne.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.db.CharacterSkill;
import cz.panjeskyne.model.xml.Skill;
import cz.panjeskyne.model.xml.Statistic;
import cz.panjeskyne.service.formula.FormulaException;
import cz.panjeskyne.service.formula.Result;
import cz.panjeskyne.test.AbstractSpringTest;

public class StatisticServiceImplTest extends AbstractSpringTest {

	@Autowired
	private StatisticServiceImpl service;
	
	@Autowired
	private SkillServiceImpl skills;
	
	@Test
	public void getByCodeName() {
		Statistic stat = service.getByCodename("statistic.sil");
		assertEquals(stat.getId(), "statistic.sil");
	}
	
	@Test
	public void getDependentStatistics() {
		List<Statistic> actual = service.getDependentStatistics("statistic.obr");
		List<Statistic> expected = new ArrayList<>();
		expected.add(service.getByCodename("statistic.krs"));
		expected.add(service.getByCodename("statistic.magic"));
		expected.add(service.getByCodename("statistic.f2f"));
		expected.add(service.getByCodename("statistic.def"));
		assertThat(actual, is(expected));
	}
	
	@Test
	public void getGetCharacterStatistic() throws FormulaException {
		Character character = new Character();
		character.setKindCodename("dwarf");
		
		Result result;
		
		result = service.getValue(character, "statistic.odl");
		if (result.isSuccessful()) {
			assertEquals("špatná hodnota odolnosti", (Double) result.getValue(), (Double) 2.0);
		} else {
			throw result.getException();
		}
		result = service.getValue(character, "statistic.zdr");
		if (result.isSuccessful()) {
			assertEquals("špatná hodnota zdraví", (Double) result.getValue(), (Double) 8.0);
		} else {
			throw result.getException();
		}
		
		List<Statistic> statistics = service.getStatisticsByGroup("main");
		assertEquals("špatný počet prvků skupiny statistik", 6, statistics.size());
	}
	
	@Test
	public void formulaComputing() throws Exception {
		Character character = new Character();
		character.setKindCodename("dwarf");
		
		character.setSkills(new ArrayList<>()); // not loaded by default
	    skills.getByCodename("skill.sil").learnSkill(character, 1);
		
	    // Standard formula with parenthesis testing
		test(character, "-1",                            -1.0);
		test(character, "((1+(1))+((1)+1))",              4.0);
		test(character, "(1+2)/3",                        1.0);
		test(character, "1+2/3",                          1.6667);
		test(character, "1/(2+3)*3",                      0.6);
		test(character, "(1/2)+(3*+3)",                   9.5);
		test(character, "(1/2)+(3*3)",                    9.5);
		
		// Condition testing
		test(character, "1>0",                            1.0);
		test(character, "1<0",                            0.0);
		test(character, "1>=0",                           1.0);
		test(character, "1>=1",                           1.0);
		test(character, "1>=3/3",                         1.0);
		test(character, "1<=0",                           0.0);
		test(character, "1>=-1",                          1.0);
		test(character, "1=-1",                           0.0);
		test(character, "1=1",                            1.0);
		
		// Table testing
		test(character, "table.zz(5,5)",                  6.0);
		test(character, "table.life(0)",                  3.0);
		
		// Function testing
		test(character, "(function.abs_i((((-1.1)))))",   1.0);
		test(character, "(function.abs((((-1.1)))))",     1.1);
		test(character, "function.roundup(1+2.5)",        4.0);
		test(character, "function.roundup((1/2)+(3*3))", 10.0);
	    
		// Statistic referenced value
		test(character, "statistic.sil",                  2.0);
		test(character, "character.zivoty",               9.0);
		
		skills.getByCodename("skill.sil").learnSkill(character, 2);
		
		test(character, "statistic.sil",                  3.0);
		test(character, "character.zivoty",              10.0);
	}

	private void test(Character character, String function, double expected) throws Exception {
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance();
		formatter.applyPattern("0.####");
		
		Result result = service.getFormulaValue(character, function);
		if (result.isSuccessful()) {
			assertEquals(function + "=", formatter.format(expected), formatter.format(result.getValue()));
		} else {
			throw new Exception(function + " :: " + result.getException().getLocalizedMessage(), result.getException());
		}
	}
}
