package cz.deznekcz.games.panjeskyne.service.formula.element;

import cz.deznekcz.games.panjeskyne.i18n.I18N;
import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Statistic;
import cz.deznekcz.games.panjeskyne.service.StatisticService;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaElement;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaException;

public class StatisticElement extends FormulaElement {

	private Statistic statistic;

	public StatisticElement(Statistic statistic) {
		this.statistic = statistic;
	}

	@Override
	public double getValue(StatisticService provider, Character character) throws FormulaException {
		return validate(provider.getValue(character, statistic));
	}

	@Override
	public FormulaElement applyChild(FormulaElement child) throws FormulaException {
		if (child.isSimpleType()) {
			throw new FormulaException(I18N.argumented(I18N.CHILDREN_NOT_IMPLEMENTED, I18N.id(getClass().getName())));
		} else if (!child.isSimpleType()) {
			this.parent = child;
			child.applyChild(this);
			return child;
		}
		else {
			throw new FormulaException(I18N.HAS_PARENT_ELEMENT);
		}
	}
	
	@Override
	public String toString() {
		return statistic.getCodename() != null ? statistic.getCodename() : "[NOT_DEFINED]";
	}

}
