package cz.deznekcz.games.panjeskyne.service.formula.element;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.i18n.I18N;
import cz.deznekcz.games.panjeskyne.service.StatisticService;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaElement;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaException;
import cz.deznekcz.games.panjeskyne.service.formula.OperandType;

public class OperatorElement extends FormulaElement {

	private OperandType operandType;

	public OperatorElement(OperandType operatorType) {
		this.operandType = operatorType;
	}

	@Override
	public double getValue(StatisticService provider, Character character) throws FormulaException {
		return operandType.apply(calculateOperands(provider, character));
	}

	@Override
	public FormulaElement applyChild(FormulaElement child) throws FormulaException {
		if (operandType.getOperands() > operands.size() && !child.isOperator()) {
			child.parent = this;
			operands.add(child);
			return child.isSimpleType() ? this : child;
		} else if (child.isOperator()) {
			boolean isEqualOperator = cast(child).operandType.equals(OperandType.EQ);
			if (!isEqualOperator && operandType.getPriority() >= cast(child).operandType.getPriority()) {
				child.operands.add(this);
				child.parent = this.parent;
				this.parent = child;
				return child;
			} else if (!isEqualOperator) {
				child.parent = this;
				child.operands.add(this.operands.get(1));
				this.operands.get(1).parent = child;
				this.operands.set(1, child);
				return child;
			} else if(isEqualOperator) {
				operandType = OperandType.valueOf(operandType.name() + "E");
				return this;
			} else {
				throw new FormulaException(I18N.argumented(I18N.OPERATOR_INVALID, I18N.id(cast(child).operandType.getOperator())));
			}
		} else {
			throw new FormulaException(I18N.argumented(I18N.TOO_MUCH_OPERANDS, I18N.id(getClass().getName())));
		}
	}

	private OperatorElement cast(FormulaElement child) {
		return (OperatorElement) child;
	}

	@Override
	public String toString() {
		return operandType.toString(operands);
	}
}
