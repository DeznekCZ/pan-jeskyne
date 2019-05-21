package cz.panjeskyne.service.formula;

import java.util.ArrayList;
import java.util.List;

import cz.panjeskyne.i18n.I18N;
import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.xml.Statistic;
import cz.panjeskyne.service.StatisticService;
import cz.panjeskyne.service.formula.element.BracketElement;
import cz.panjeskyne.service.formula.element.FunctionElement;
import cz.panjeskyne.service.formula.element.NextElement;
import cz.panjeskyne.service.formula.element.NumberElement;
import cz.panjeskyne.service.formula.element.OperatorElement;
import cz.panjeskyne.service.formula.element.StatisticElement;

public abstract class FormulaElement {
	
	public static FormulaElement function(String codename) throws FormulaException {
		return new FunctionElement(codename);
	}

	public boolean isNext() {
		return this instanceof NextElement;
	}

	public String toStringOperands() {
		StringBuffer sb = new StringBuffer();
		
		for (FormulaElement formulaElement : operands) {
			sb.append(",");
			sb.append(formulaElement.toString());
		}
		
		return sb.toString().substring(1);
	}

	public static FormulaElement variable(Statistic statistic) throws FormulaException {
		return new StatisticElement(statistic);
	}

	public static FormulaElement bracket() throws FormulaException {
		return new BracketElement();
	}
	
	public static FormulaElement number(Double number) throws FormulaException {
		return new NumberElement(number);
	}

	public List<FormulaElement> operands = new ArrayList<>(2);
	public FormulaElement parent;

	public boolean isSimpleType() {
		return this instanceof NumberElement || this instanceof StatisticElement;
	}

	public abstract double getValue(StatisticService provider, Character character) throws FormulaException;
	
	public Double validate(Result result) throws FormulaException {
		if (!result.isSuccessful()) throw result.getException();
		return result.getValue();
	}

	protected double[] calculateOperands(StatisticService provider, Character character) throws FormulaException {
		double[] numbers = new double[operands.size()];
		int i = 0;
		for (FormulaElement operand : operands) {
			numbers[i] = operand.getValue(provider, character);
			i++;
		}
		return numbers;
	}
	
	public boolean isOperator() {
		return this instanceof OperatorElement;
	}
	
	public boolean isStatistic() {
		return this instanceof StatisticElement;
	}
	
	public boolean isBracketOrFunction() {
		return this instanceof BracketElement;
	}

	public FormulaElement applyClose() throws FormulaException {
		if (this.isCloseAble()) {
			((BracketElement) this).setClosed(true);
			return this;
			
		} else {
			if (hasParent()) {
				return getParent().applyClose();
			} else {
				throw new FormulaException(I18N.MISSING_OPENING_BRACKET);
			}
		}
	}

	public FormulaElement getParent() {
		return parent;
	}

	protected boolean hasParent() {
		return parent != null;
	}

	public FormulaElement setParent(FormulaElement parent) throws FormulaException {
		if (parent != null) {
			return parent.applyChild(this);
		} else {
			return this;
		}
	}
	
	public abstract FormulaElement applyChild(FormulaElement child) throws FormulaException;

	public boolean isCloseAble() {
		return this instanceof BracketElement && !((BracketElement) this).isClosed();
	}

	public static FormulaElement operator(OperandType type) {
		return new OperatorElement(type);
	}

	public static FormulaElement next() {
		return new NextElement();
	}
}
