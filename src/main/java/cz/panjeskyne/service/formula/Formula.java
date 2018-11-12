package cz.panjeskyne.service.formula;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.panjeskyne.i18n.I18N;
import cz.panjeskyne.model.Statistic;
import cz.panjeskyne.service.Result;
import cz.panjeskyne.service.StatisticService;

public class Formula {
	
	private static final Pattern WHITESPACES = Pattern.compile("\\s+");
	
	private Statistic statistic;

	private FormulaElement rootElement;

	private String computingFormula;

	public Formula(String formula) {
	}

	public Formula(Statistic statistic) {
		this.statistic = statistic;
	}

	public static Result parse(Statistic statistic) {
		Result result = new Result();
		Formula formula = new Formula(statistic);
		result.setFormula(formula);
		
		formula.clearWhiteSpaces();
		try {
			formula.start(result);
		} catch (FormulaException e) {
			result.setException(e);
		}
		
		return result;
	}
	
	private static final Pattern SEARCH = Pattern.compile("([0-9]+([.][0-9]+)?)|((\\w+(.\\w+)+)([(])?)");
	
	protected void start(Result result) throws FormulaException {
		Matcher matcher = SEARCH.matcher(computingFormula);
		int end = 0;
		int start = 0;
		
		FormulaElement fe = null;
		
		while(matcher.find()) {
			end = matcher.start();
			if (start != end) {
				for (char operator : computingFormula.substring(start, end).toCharArray()) {
					OperandType type = OperandType.getType(operator);
					if (type == OperandType.BRACKET_END) {
						if (fe == null || !fe.applyClose()) 
							throw new FormulaException(I18N.MISSING_OPENING_BRACKET);
					} else if (type == OperandType.BRACKET_START) {
						fe = FormulaElement.bracket().setParent(fe);
					} else if (type == OperandType.NEXT) {
						// skip, handled by adding operands
					} else {
						fe = FormulaElement.operator(type).setParent(fe);
					}
				}
			}
			if (matcher.group(5) != null) {
				// READ FUNCTION
				String identifier = matcher.group(5);
				fe = FormulaElement.function(identifier).setParent(fe);
			} else if (matcher.group().matches("[0-9]+([.][0-9]+)?")) {
				// READ NUMBER
				Double number = Double.parseDouble(matcher.group());
				fe = FormulaElement.number(number).setParent(fe); 
			} else {
				// READ IDENTIFIER
				String identifier = matcher.group();
				fe = FormulaElement.variable(identifier).setParent(fe); 
			}
			start = matcher.end();
		}
		
		while(fe.hasParent()) {
			fe = fe.getParent();
		}
		
		setRootElement(fe);
	}

	protected void clearWhiteSpaces() {
		Matcher matcher = WHITESPACES.matcher(getFormulaString());
		StringBuffer buffer = new StringBuffer(getFormulaString().length());
		while (matcher.find()) {
			matcher.appendReplacement(buffer, "");
		}
		matcher.appendTail(buffer);
		computingFormula = buffer.toString();
	}

	public String getFormulaString() {
		return getStatistic().hasFormula() ? getStatistic().getFormula() : "";
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
