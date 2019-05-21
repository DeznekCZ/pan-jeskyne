package cz.panjeskyne.service.formula;

import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.service.StatisticService;

public class Result {

	private boolean successful;
	private Formula formula;
	private double value;
	private FormulaException exception;

	public Result() {
		this.successful = true;
		this.value = new Double(0);
	}
	
	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}
	
	public boolean isSuccessful() {
		return successful;
	}

	public void setFormula(Formula formula) {
		this.formula = formula;
	}
	
	public Formula getFormula() {
		return formula;
	}

	public void applyFormula(StatisticService provider, Character character) {
		try {
			this.value = formula.getRootElement().getValue(provider, character);
		} catch (FormulaException e) {
			this.setException(e);
		}
	}

	public void increase(double statistic) {
		if (this.successful)
			this.value += statistic;
	}

	public boolean getBoolean() {
		return value != 0.0;
	}

	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	public void setException(FormulaException exception) {
		this.exception = exception;
		this.successful = exception == null;
	}

	public FormulaException getException() {
		return exception;
	}

	public void multiply(double statistic) {
		if (this.successful)
			this.value *= statistic;
	}
}
