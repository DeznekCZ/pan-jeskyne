package cz.deznekcz.games.panjeskyne.service.formula.element;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.service.StatisticService;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaElement;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaException;

public class NextElement extends FormulaElement {

	@Override
	public double getValue(StatisticService provider, Character character) throws FormulaException {
		return 0;
	}

	@Override
	public FormulaElement applyChild(FormulaElement child) throws FormulaException {
		return null;
	}

}
