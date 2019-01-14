package cz.panjeskyne.service.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.panjeskyne.i18n.I18N;
import cz.panjeskyne.model.xml.Statistic;
import cz.panjeskyne.service.Result;
import cz.panjeskyne.service.StatisticService;

public class Formula {
	
	private Statistic statistic;

	private FormulaElement rootElement;

	private String computingFormula;

	public Formula(String formula) {
	}

	public Formula(Statistic statistic) {
		this.statistic = statistic;
	}

	public static Result parse(StatisticService provider, Statistic statistic) {
		Result result = new Result();
		Formula formula = new Formula(statistic);
		result.setFormula(formula);
		
		formula.clearWhiteSpaces();
		formula.normalizeOperators();
		try {
			formula.start(provider, result);
		} catch (FormulaException e) {
			result.setException(e);
		}
		
		return result;
	}

	private static final Pattern WHITESPACES = Pattern.compile("\\s+");

	private void clearWhiteSpaces() {
		Matcher matcher = WHITESPACES.matcher(getFormulaString());
		StringBuffer buffer = new StringBuffer(getFormulaString().length());
		while (matcher.find()) {
			matcher.appendReplacement(buffer, "");
		}
		matcher.appendTail(buffer);
		computingFormula = buffer.toString();
	}
	
	private static final Pattern INVERT = Pattern.compile("([^)*/0-9a-zA-Z]|^)(-|[+])");
	
	private void normalizeOperators() {
		Matcher matcher = INVERT.matcher(computingFormula
				.replace("+-", "-")
				.replace("--", "+")
				.replace("*-", "*(0-1)*")
				.replace("/-", "*(0-1)/")
				.replace("*+", "*")
				.replace("/+", "/")
				.replace(">+", ">")
				.replace("<+", "<")
				.replace(">-", ">0-")
				.replace("<-", "<0-")
				.replace(">=+", ">=")
				.replace("<=+", "<=")
				.replace(">=-", ">=0-")
				.replace("<=-", "<=0-")
				.replace("=+", "=")
				.replace("=-", "=0-")
				);
		StringBuffer buffer = new StringBuffer(getFormulaString().length());
		while (matcher.find()) {
			matcher.appendReplacement(buffer, matcher.group(1));
			buffer.append("0");
			buffer.append(matcher.group(2));
		}
		matcher.appendTail(buffer);
		computingFormula = buffer.toString();
	}

	private static final Pattern SEARCH = Pattern.compile("([0-9]+([.][0-9]+)?)|((\\w+([.]\\w+)+)([(])?)");
	
	protected void start(StatisticService provider, Result result) throws FormulaException {
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
						if (fe == null) 
							throw new FormulaException(I18N.MISSING_OPENING_BRACKET);
						else
							fe = fe.applyClose();
					} else if (type == OperandType.BRACKET_START) {
						fe = FormulaElement.bracket().setParent(fe);
					} else if (type == OperandType.NEXT) {
						fe = FormulaElement.next().setParent(fe);
					} else {
						fe = FormulaElement.operator(type).setParent(fe);
					}
				}
			}
			if (matcher.group(6) != null) {
				// READ FUNCTION
				String identifier = matcher.group(4);
				fe = FormulaElement.function(identifier).setParent(fe);
			} else if (matcher.group().matches("[0-9]+([.][0-9]+)?")) {
				// READ NUMBER
				Double number = Double.parseDouble(matcher.group());
				fe = FormulaElement.number(number).setParent(fe); 
			} else {
				// READ IDENTIFIER
				String identifier = matcher.group();
				Statistic lStatistic = provider.getByCodename(identifier);
				if (lStatistic == null)
					throw new FormulaException(I18N.argumented(I18N.DATA_NOT_FOUND, I18N.id(identifier)));
				fe = FormulaElement.variable(lStatistic).setParent(fe); 
			}
			start = matcher.end();
		}
		
		while(fe.hasParent()) {
			fe = fe.getParent();
		}
		
		setRootElement(fe);
	}

	public String getFormulaString() {
		return getStatistic().hasFormula() ? getStatistic().getFormula() : "";
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public static List<Statistic> getReferencedStatistics(Statistic statistic) {
		return new ArrayList<>();// StatisticService.getStatisticsWhereFormulaContains(statistic.getCodename());
	}

	public FormulaElement getRootElement() {
		return rootElement;
	}

	public void setRootElement(FormulaElement rootElement) {
		this.rootElement = rootElement;
	}

}
