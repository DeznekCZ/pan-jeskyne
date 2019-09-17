package cz.deznekcz.games.panjeskyne.service.formula.element;

import cz.deznekcz.games.panjeskyne.i18n.I18N;
import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.service.StatisticService;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaElement;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaException;

public class BracketElement extends FormulaElement {

	private boolean closed;

	public BracketElement() {
		this.closed = false;
	}
	
	@Override
	public double getValue(StatisticService provider, Character character) throws FormulaException {
		if (operands.size() != 1)
			throw new FormulaException(I18N.BRACKET_INVALID);
		return operands.get(0).getValue(provider, character);
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean isClosed() {
		return closed;
	}

	@Override
	public FormulaElement applyChild(FormulaElement child) throws FormulaException {
		if (isClosed() && child.isSimpleType()) {
			throw new FormulaException(I18N.BRACKET_IS_CLOSED);
		} else if (operands.size() < 1 && !isClosed()) {
			this.operands.add(child);
			child.parent = this;
			return child.isSimpleType() ? this : child;
		} else if (operands.size() == 1 && !child.isSimpleType() && !isClosed() && !child.isNext()) {
			FormulaElement tmp = this.operands.get(0);
			this.operands.set(0, child);
			child.parent = this;
			child.applyChild(tmp);
		} else if ((isClosed() || child.isNext()) && !child.isSimpleType()) {
			if (hasParent()) {
				return getParent().applyChild(child);
			} else {
				child.applyChild(this);
			}
		} else {
			throw new FormulaException(I18N.argumented(I18N.TOO_MUCH_OPERANDS, I18N.id(getClass().getName())));
		}
		return child;
	}

	@Override
	public String toString() {
		return operands.size() > 0 ? ("("+operands.get(0).toString()+")") : ("([NOT_DEFINED])");
	}
}
