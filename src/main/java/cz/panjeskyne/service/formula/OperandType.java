package cz.panjeskyne.service.formula;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import cz.panjeskyne.i18n.I18N;

public enum OperandType {
	SUM('+', 2, 0, v -> v[0].doubleValue() + v[1].doubleValue(), (op,ot) -> ot.getOperand(op, 0) + ot.getOperator() + ot.getOperand(op, 1)),
	SUB('-', 2, 1, v -> v[0].doubleValue() - v[1].doubleValue(), (op,ot) -> ot.getOperand(op, 0) + ot.getOperator() + ot.getOperand(op, 1)),
	MUL('*', 2, 2, v -> v[0].doubleValue() * v[1].doubleValue(), (op,ot) -> ot.getOperand(op, 0) + ot.getOperator() + ot.getOperand(op, 1)),
	DIV('/', 2, 3, v -> v[0].doubleValue() / v[1].doubleValue(), (op,ot) -> ot.getOperand(op, 0) + ot.getOperator() + ot.getOperand(op, 1)), 
	BRACKET_END(')', 0, 4, v -> 0.0, (op,ot) -> ""), 
	BRACKET_START('(', 1, 5, v -> 0.0, (op,ot) -> ""), 
	NEXT(',', 1, 5, v -> 0.0, (op,ot) -> "");
	
	private char operator;
	private int operands;
	private int priority;
	private Function<Double[],Double> function;
	private BiFunction<List<FormulaElement>, OperandType, String> toString;
	
	private OperandType(char operator, int operands, int priority, 
			Function<Double[],Double> function, BiFunction<List<FormulaElement>,OperandType,String> toString) {
		this.operator = operator;
		this.operands = operands;
		this.priority = priority;
		this.function = function;
		this.toString = toString;
	}

	public int getOperands() {
		return operands;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public char getOperator() {
		return operator;
	}
	
	public static OperandType getType(char operator) {
		OperandType found = null;
		for (OperandType type : values()) {
			if (type.getOperator() == operator) {
				found = type;
				break;
			}
		}
		return found;
	}

	public Double apply(Double[] operands) throws FormulaException {
		if (operands == null || operands.length != getOperands())
			throw new FormulaException(I18N.argumented(I18N.OPERATOR_INVALID, 
					I18N.id(String.valueOf(getOperator())),
					I18N.number(getOperands()), I18N.number(operands == null ? 0 : operands.length)));
		return function.apply(operands);
	}
	
	private String getOperand(List<FormulaElement> op, int i) {
		if (op.size() > i) {
			return op.get(i).toString();
		} else {
			return "[NOT_DEFINED]";
		}
	}

	public String toString(List<FormulaElement> operands) {
		return toString.apply(operands, this);
	}
}
