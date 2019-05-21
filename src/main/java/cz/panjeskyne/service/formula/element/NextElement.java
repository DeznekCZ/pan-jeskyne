package cz.panjeskyne.service.formula.element;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.service.StatisticService;
import cz.panjeskyne.service.formula.FormulaElement;
import cz.panjeskyne.service.formula.FormulaException;

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
