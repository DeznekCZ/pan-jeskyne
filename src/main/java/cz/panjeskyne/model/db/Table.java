package cz.panjeskyne.model.db;

import cz.panjeskyne.service.formula.FormulaException;

public class Table {

	private int argsCount;
	private String codename;

	public double getValue(double[] numbers) throws FormulaException {
		return 0.0;
	}

	public void setArgsCount(int argsCount) {
		this.argsCount = argsCount;
	}
	
	public int getArgsCount() {
		return argsCount;
	}
	
	public void setCodename(String codename) {
		this.codename = codename;
	}

	public String getCodename() {
		return codename;
	}

}