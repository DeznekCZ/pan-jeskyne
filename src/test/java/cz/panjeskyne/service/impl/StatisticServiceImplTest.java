package cz.panjeskyne.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Statistic;
import cz.panjeskyne.service.Result;
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
	public void getGetCharacterStatistic() {
		Character character = new Character();
		character.setKindCodename("dwarf.dwarf");
		
		Result result = service.getValue(character, "statistic.odl");
		if (result.isSuccessful()) {
			System.out.println(result.getValue());
		} else {
			result.getException().printStackTrace(System.err);
		}
	}
	
	@Test
	public void formulaComputing() throws Exception {
		Character character = new Character();
		character.setKindCodename("dwarf.dwarf");

		Result result;
		
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance();
		formatter.applyPattern("0.####");
		
		for (String string : Arrays.asList(
				"table.zz(5,5)", "table.life(0)",
				"function.roundup(1+2)", "-1", "(function.abs_i((((-1.1)))))", "((1+(1))+((1)+1))", "(1+2)/3", "1+2/3", "1/(2+3)*3", 
				"(1/2)+(3*+3)", "((1/2)+(3*3))", "function.roundup((1/2)+(3*3))"
			)) {
			result = service.getValue(character, formula(string));
			if (result.isSuccessful())
				System.out.format("%s=%s%n", string, formatter.format(result.getValue()));
			else
				System.err.println(string + "=" + result.getException().getLocalizedMessage());
		}
	}

	private static Statistic formula(String string) {
		Statistic statistic = new Statistic();
		statistic.setFormula(string);
		return statistic;
	}
}
