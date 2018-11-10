package cz.panjeskyne.service.providers;

import cz.panjeskyne.model.Character;

public class Result {

	private boolean successful;
	private Formula formula;
	private Number value;

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
		this.value = StatisticProvider.getValue(character, formula.getStatistic()).value;
	}

	public void increase(Number statistic) {
		this.value = statistic.doubleValue() + this.value.doubleValue();
	}

	public Number getValue() {
		return value;
	}
	
	public void setValue(Number value) {
		this.value = value;
	}
}
