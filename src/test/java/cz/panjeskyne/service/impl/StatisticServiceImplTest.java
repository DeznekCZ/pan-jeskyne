package cz.panjeskyne.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Statistic;
import cz.panjeskyne.service.Result;
import cz.panjeskyne.service.formula.FormulaException;
import cz.panjeskyne.test.AbstractSpringTest;

public class StatisticServiceImplTest extends AbstractSpringTest {
	
	@Autowired
	private StatisticServiceImpl service;
	
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
		
		Result result = service.getValue(character, "statistic.odl");
		if (result.isSuccessful()) {
			assertEquals((Double) 2.0, (Double) result.getValue());
		} else {
			throw result.getException();
		}
	}
	
	@Test
	public void formulaComputing() throws FormulaException {
		Character character = new Character();
		character.setKindCodename("dwarf");

		Result result;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance();
		formatter.applyPattern("0.####");
		
		Map<String, Double> results = new HashMap<>();
		results.put("table.zz(5,5)",                  6.0);
		results.put("table.life(0)",                  3.0);
		results.put("function.roundup(1+2.5)",        4.0);
		results.put("-1",                            -1.0);
		results.put("(function.abs_i((((-1.1)))))",   1.0);
		results.put("(function.abs((((-1.1)))))",     1.1);
		results.put("((1+(1))+((1)+1))",              4.0);
		results.put("(1+2)/3",                        1.0);
		results.put("1+2/3",                          1.6667);
		results.put("1/(2+3)*3",                      0.6);
		results.put("(1/2)+(3*+3)",                   9.5);
		results.put("(1/2)+(3*3)",                    9.5);
		results.put("function.roundup((1/2)+(3*3))", 10.0);
		
		for (String string : results.keySet()) {
			result = service.getValue(character, formula(string));
			if (result.isSuccessful()) {
				assertEquals(formatter.format(result.getValue()), formatter.format(results.get(string)));
			} else {
				throw result.getException();
			}
		}
	}

	private static Statistic formula(String string) {
		Statistic statistic = new Statistic();
		statistic.setFormula(string);
		return statistic;
	}
}
