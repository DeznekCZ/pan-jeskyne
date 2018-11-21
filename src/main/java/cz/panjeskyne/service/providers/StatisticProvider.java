package cz.panjeskyne.service.providers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Statistic;
import cz.panjeskyne.service.KindService;
import cz.panjeskyne.service.Result;
import cz.panjeskyne.service.formula.Formula;

@Component
public class StatisticProvider {

	@Autowired
	private KindService kindService;

	/**
	 * table.main.zivot_a_unava((statistic.main.sila+statistic.main.obratnost)/2
	 * @param statistic
	 * @return
	 */
	public Result validateFormula(String formula) {
		Statistic statistic = new Statistic();
		statistic.setFormula(formula);
		return parseFormula(statistic);
	}
	
	private Result parseFormula(Statistic statistic) {
		return Formula.parse(statistic);
	}

	public Result getValue(Character character, Statistic statistic) {
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
			result.increase(kindService.getCharactersKind(character).getStatistic(statistic.getCodename()));
		}
		
		return result;
	}

	public List<Statistic> updateByStatistic(Statistic statistic) {
		return Formula.getReferencedStatistics(statistic);
	}
}
