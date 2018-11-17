package cz.panjeskyne.service.providers;

import java.util.List;

import cz.panjeskyne.model.Statistic;
import cz.panjeskyne.service.KindService;
import cz.panjeskyne.service.Result;
import cz.panjeskyne.service.SkillService;
import cz.panjeskyne.service.formula.Formula;
import cz.panjeskyne.model.Character;

public class StatisticProvider {

	/**
	 * table.main.zivot_a_unava((statistic.main.sila+statistic.main.obratnost)/2
	 * @param statistic
	 * @return
	 */
	public static Result validateFormula(String formula) {
		Statistic statistic = new Statistic();
		statistic.setFormula(formula);
		return parseFormula(statistic);
	}
	
	private static Result parseFormula(Statistic statistic) {
		return Formula.parse(statistic);
	}

	public static Result getValue(Character character, Statistic statistic) {
		Result result = new Result(); 
		
		if (statistic.hasFormula()) {
			Result inMiddle = parseFormula(statistic);
			if (!inMiddle.isSuccessful())
				return inMiddle;
			
			inMiddle.applyFormula(character);
			result.setValue(inMiddle.getValue());
			result.setException(inMiddle.getException());
		}
		
		if (result.isSuccessful()) {
			result.increase(character.getKind().getStatistic(statistic.getCodename()));
		}
		
		return result;
	}

	public static List<Statistic> updateByStatistic(Statistic statistic) {
		return Formula.getReferencedStatistics(statistic);
	}
}
