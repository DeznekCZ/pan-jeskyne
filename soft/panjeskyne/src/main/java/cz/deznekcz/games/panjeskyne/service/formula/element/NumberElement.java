package cz.deznekcz.games.panjeskyne.service.formula.element;

import cz.deznekcz.games.panjeskyne.i18n.I18N;
import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.service.StatisticService;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaElement;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaException;

public class NumberElement extends FormulaElement {

	private Double number;
	
	public NumberElement(Double number) {
		this.number = number;
	}

	@Override
	public double getValue(StatisticService provider, Character character) throws FormulaException {
		return number;
	}

	@Override
	public FormulaElement applyChild(FormulaElement child) throws FormulaException {
		if (child.isSimpleType() && !hasParent()) {
			throw new FormulaException(I18N.argumented(I18N.CHILDREN_NOT_IMPLEMENTED, I18N.id(getClass().getName())));
		} else if (!child.isSimpleType() && !hasParent()) {
			return child.applyChild(this);
		} else if (hasParent()) {
			return getParent().applyChild(child);
		} else {
			throw new FormulaException(I18N.HAS_PARENT_ELEMENT);
		}
	}
	
	@Override
	public String toString() {
		return number != null ? String.valueOf(number) : "[NOT_DEFINED]";
	}

}
