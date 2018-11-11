package cz.panjeskyne.service.formula;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.panjeskyne.model.Statistic;
import cz.panjeskyne.service.Result;
import cz.panjeskyne.service.StatisticService;

public class Formula {
	
	private static final Pattern WHITESPACES = Pattern.compile("\\s+");
	
	private Statistic statistic;
	private String formulaString;

	private FormulaElement rootElement;

	public Formula(String formula) {
		this.formulaString = formula == null ? "" : formula;
	}

	public static Result parse(Statistic statistic) {
		Result result = new Result();
		Formula formula = new Formula(statistic.getFormula());
		result.setFormula(formula);
		
		formula.clearWhiteSpaces();
		try {
			formula.start(result);
		} catch (FormulaException e) {
			result.setException(e);
		}
		
		return result;
	}
	
	private static final Pattern SEARCH = Pattern.compile("([0-9]+([.][0-9]+))|(((\\w+(.\\w+)+)([(]))?)");
	
	protected void start(Result result) throws FormulaException {
		Matcher matcher = SEARCH.matcher(getFormulaString());
		int start = 0;
		int end = 0;
		while(matcher.find()) {
			if (!matcher.group(5).isEmpty()) {
				// READ FUNCTION
				String identifier = matcher.group(3);
				FormulaElement fe = FormulaElement.function(identifier);
			} else if (matcher.group().matches("[0-9]+([.][0-9]+)")) {
				// READ NUMBER
				Double number = Double.parseDouble(matcher.group());
				FormulaElement fe = FormulaElement.number(number); 
			} else {
				// READ IDENTIFIER
				String identifier = matcher.group();
				FormulaElement fe = FormulaElement.variable(identifier); 
			}
		}
	}

	protected void clearWhiteSpaces() {
		Matcher matcher = WHITESPACES.matcher(formulaString);
		StringBuffer buffer = new StringBuffer(formulaString.length());
		while (matcher.find()) {
			matcher.appendReplacement(buffer, "");
		}
		matcher.appendTail(buffer);
		formulaString = buffer.toString();
	}

	public String getFormulaString() {
		return formulaString;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public static List<Statistic> getReferencedStatistics(Statistic statistic) {
		return StatisticService.getStatisticsWhereFormulaContains(statistic.getCodename());
	}

	public FormulaElement getRootElement() {
		return rootElement;
	}

	public void setRootElement(FormulaElement rootElement) {
		this.rootElement = rootElement;
	}

}
