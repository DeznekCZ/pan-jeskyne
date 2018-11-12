package cz.panjeskyne.model;

import cz.panjeskyne.service.formula.FunctionTypes;

public class Function {

	public String getCodename() {
		return "function.abs";
	}
	
	public FunctionTypes getType() {
		return FunctionTypes.MATH;
	}

	public String getFormula() {
		return "java.lang.Math.abs(java.lang.Double)";
	}

}
