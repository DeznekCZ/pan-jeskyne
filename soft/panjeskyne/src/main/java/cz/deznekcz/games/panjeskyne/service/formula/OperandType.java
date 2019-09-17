package cz.deznekcz.games.panjeskyne.service.formula;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import cz.deznekcz.games.panjeskyne.i18n.I18N;

public enum OperandType {
	BRACKET_END(')', 0, 0, v -> 0.0, (op,ot) -> ""), 
	BRACKET_START('(', 1, 0, v -> 0.0, (op,ot) -> ""), 
	NEXT(',', 1, 0, v -> 0.0, (op,ot) -> ""),
	GT('>', 2, 1, v -> v[0] > v[1] ? 1.0 : 0.0, standardToString()),
	LT('<', 2, 1, v -> v[0] < v[1] ? 1.0 : 0.0, standardToString()),
	EQ('=', 2, 1, v -> v[0] == v[1] ? 1.0 : 0.0, standardToString()),
	GTE(']', 2, 1, v -> v[0] >= v[1] ? 1.0 : 0.0, (op,ot) -> ot.getOperand(op, 0) + ">=" + ot.getOperand(op, 1)),
	LTE('[', 2, 1, v -> v[0] <= v[1] ? 1.0 : 0.0, (op,ot) -> ot.getOperand(op, 0) + "<=" + ot.getOperand(op, 1)),
	SUM('+', 2, 2, v -> v[0] + v[1], standardToString()),
	SUB('-', 2, 2, v -> v[0] - v[1], standardToString()),
	MUL('*', 2, 4, v -> v[0] * v[1], standardToString()),
	DIV('/', 2, 5, v -> v[0] / v[1], standardToString()); 
	
	
	private char operator;
	private int operands;
	private int priority;
	private Function<double[],Double> function;
	private BiFunction<List<FormulaElement>, OperandType, String> toString;
	
	private OperandType(char operator, int operands, int priority, 
			Function<double[],Double> function, BiFunction<List<FormulaElement>,OperandType,String> toString) {
		this.operator = operator;
		this.operands = operands;
		this.priority = priority;
		this.function = function;
		this.toString = toString;
	}

	private static BiFunction<List<FormulaElement>,OperandType,String> standardToString() {
		return (op,ot) -> ot.getOperand(op, 0) + ot.getOperator() + ot.getOperand(op, 1);
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

	public Double apply(double[] ds) throws FormulaException {
		if (ds == null || ds.length != getOperands())
			throw new FormulaException(I18N.argumented(I18N.OPERATOR_INVALID, 
					I18N.id(String.valueOf(getOperator())),
					I18N.number(getOperands()), I18N.number(ds == null ? 0 : ds.length)));
		return function.apply(ds);
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
