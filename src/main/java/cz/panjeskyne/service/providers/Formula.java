package cz.panjeskyne.service.providers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.panjeskyne.model.Statistic;
import cz.panjeskyne.service.StatisticService;

public class Formula {
	
	private static final Pattern WHITESPACES = Pattern.compile("\\s+");
	
	private Statistic statistic;
	private String formulaString;

	public Formula(String formula) {
		this.formulaString = formula == null ? "" : formula;
	}

	public static Result parse(Statistic statistic) {
		Result result = new Result();
		Formula formula = new Formula(statistic.getFormula());
		result.setFormula(formula);
		
		formula.clearWhiteSpaces();
		formula.start(result);
		
		return result;
	}

	private void start(Result result) {
		
	}

	private void clearWhiteSpaces() {
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

}
