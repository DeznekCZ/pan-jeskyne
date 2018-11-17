package cz.panjeskyne.model;

import cz.panjeskyne.service.formula.FunctionTypes;

public class Function {

	private String type;
	private String codename;
	private String formula;
	private int argsCount;

	public String getCodename() {
		return codename;
	}
	
	public void setCodename(String codename) {
		this.codename = codename;
	}
	
	public FunctionTypes getType() {
		return FunctionTypes.valueOf(type);
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setType(FunctionTypes type) {
		this.type = type.name();
	}
	
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public int getArgsCount() {
		return argsCount;
	}

	public void setArgsCount(int argCount) {
		this.argsCount = argCount;
	}
}
