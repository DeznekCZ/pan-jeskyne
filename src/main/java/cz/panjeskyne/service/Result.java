package cz.panjeskyne.service;

import cz.panjeskyne.model.Character;
import cz.panjeskyne.service.formula.Formula;
import cz.panjeskyne.service.formula.FormulaException;
import cz.panjeskyne.service.providers.StatisticProvider;

public class Result {

	private boolean successful;
	private Formula formula;
	private Double value;
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

	public void applyFormula(Character character) {
		try {
			this.value = formula.getRootElement().getValue(character);
		} catch (FormulaException e) {
			this.setException(e);
		}
	}

	public void increase(Number statistic) {
		this.value = statistic.doubleValue() + this.value.doubleValue();
	}

	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}

	public void setException(FormulaException exception) {
		this.exception = exception;
		this.successful = exception == null;
	}

	public FormulaException getException() {
		return exception;
	}
}
