package cz.panjeskyne.service.formula;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.panjeskyne.i18n.I18N;
import cz.panjeskyne.model.Function;
import cz.panjeskyne.model.Statistic;
import cz.panjeskyne.model.Table;
import cz.panjeskyne.model.Character;
import cz.panjeskyne.service.FunctionService;
import cz.panjeskyne.service.Result;
import cz.panjeskyne.service.StatisticService;
import cz.panjeskyne.service.TableService;
import cz.panjeskyne.service.providers.StatisticProvider;

public abstract class FormulaElement {

	public static class MathElement extends FormulaElement {
		
		private FunctionElement element;

		public MathElement(FunctionElement element) {
			this.element = element;
		}

		@Override
		public Double getValue(Character character) throws FormulaException {
			// java.lang.Math.abs(java.lang.Double)
			String fullQualifiedName = this.element.function.getFormula();
			String className = "";
			String arguments = "";
			Matcher matcher = null;
			List<Class<?>> classes = new ArrayList<>(2);
			try {
				className = fullQualifiedName.substring(0, fullQualifiedName.split("[(]")[0].lastIndexOf('.'));
				
			} catch (RuntimeException e) {
				throw new FormulaException(I18N.argumented(I18N.NOT_MATCH_FORMAT, I18N.id(fullQualifiedName)));
			}
			try {
				Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(className);
				
				matcher = Pattern.compile("[(]((\\w+([.]\\w+)*)(,\\s*(\\w+([.]\\w+)*))*)[)]").matcher(fullQualifiedName);
				if (!matcher.find()) throw new RuntimeException();
				
				arguments = matcher.group();
				matcher = Pattern.compile("\\w+([.]\\w+)*").matcher(arguments);
				while (matcher.find()) {
					classes.add(ClassLoader.getSystemClassLoader().loadClass(matcher.group()));
				}
				Method method = clazz.getMethod(
						fullQualifiedName.substring(fullQualifiedName.split("[(]")[0].lastIndexOf('.')+1),
						classes.toArray(new Class[classes.size()])
						);
				
				method.invoke(null, this.element.operands.toArray());
				
			} catch (ClassNotFoundException e) {
				throw new FormulaException(I18N.argumented(I18N.CLASS_NOT_FOUND, I18N.id(className)));
			} catch (NoSuchMethodException e) {
				throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_FOUND, I18N.id(fullQualifiedName)));
			} catch (SecurityException | IllegalAccessException e) {
				throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_VISIBLE, I18N.id(fullQualifiedName)));
			} catch (IllegalArgumentException e) {
				throw new FormulaException(I18N.argumented(I18N.METHOD_BAD_ARGUMENTS, I18N.id(arguments)));
			} catch (InvocationTargetException e) {
				throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_INVOCATED, I18N.id(fullQualifiedName)));
			}
			return 0.0;
		}

	}

	public static class StatisticElement extends FormulaElement {

		private Statistic statistic;

		public StatisticElement(Statistic statistic) {
			this.statistic = statistic;
		}

		@Override
		public Double getValue(Character character) throws FormulaException {
			return validate(StatisticProvider.getValue(character, statistic));
		}

	}

	protected List<FormulaElement> operands = new ArrayList<>(2);
	
	public class OperatorElement extends FormulaElement {

		private OperandType operandType;

		@Override
		public Double getValue(Character character) throws FormulaException {
			return operandType.apply(calculateOperands(character));
		}
	}

	public static class NumberElement extends FormulaElement {

		private Double number;
		
		public NumberElement(Double number) {
			this.number = number;
		}

		@Override
		public Double getValue(Character character) throws FormulaException {
			return number;
		}

	}

	public static class BracketElement extends FormulaElement {

		@Override
		public Double getValue(Character character) throws FormulaException {
			if (operands.size() != 1)
				throw new FormulaException(I18N.BRACKET_INVALID);
			return operands.get(0).getValue(character);
		}

	}
	
	public static class FunctionElement extends BracketElement {

		private String identifier;
		private Function function;
		private Table table;
		private Result result;

		public FunctionElement(String identifier) throws FormulaException {
			this.identifier = identifier;
			
			check();
		}

		private void check() throws FormulaException {
			function = FunctionService.getFunction(identifier);
			if (function.getType() == FunctionTypes.TABLE) {
				table = TableService.getTable(identifier);
				if (table == null) throw new FormulaException(I18N.argumented(I18N.TABLE_NOT_FOUND, I18N.id(identifier)));
			} else if (function.getType() == FunctionTypes.FORMULA) {
				Statistic statistic = new Statistic();
				statistic.setFormula(function.getFormula());
				result = Formula.parse(statistic);
				if (!result.isSuccessful()) {
					throw result.getException();
				}
			} else if (function.getType() == FunctionTypes.MATH) {
				result = new Result();
				result.setFormula(new Formula(identifier + "()"));
				result.getFormula().setRootElement(new FormulaElement.MathElement(this));
			} else {
				throw new FormulaException(I18N.argumented(I18N.FUNCTION_NOT_FOUND, I18N.id(identifier)));
			}
		}

		@Override
		public Double getValue(Character character) throws FormulaException {
			if (function.getType() == FunctionTypes.FORMULA) {
				return result.getFormula().getRootElement().getValue(character);
			}
			if (function.getType() == FunctionTypes.TABLE) {
				if (operands.size() != table.getArgsCount()) {
					throw new FormulaException(I18N.argumented(I18N.TABLE_INVALID_ARGS_COUNT, 
							I18N.string(table.getCodename()),
							I18N.number(table.getArgsCount()), 
							I18N.number(operands.size())));
				}
				return table.getValue(calculateOperands(character));
			}
			return 0.0;
		}
	}

	public static FormulaElement function(String codename) throws FormulaException {
		return new FunctionElement(codename);
	}

	public static FormulaElement variable(String codename) throws FormulaException {
		return new StatisticElement(StatisticService.getStatistic(codename));
	}

	public static FormulaElement bracket() throws FormulaException {
		return new BracketElement();
	}
	
	public static FormulaElement number(Double number) throws FormulaException {
		return new NumberElement(number);
	}

	public abstract Double getValue(Character character) throws FormulaException;
	
	public Double validate(Result result) throws FormulaException {
		if (!result.isSuccessful()) throw result.getException();
		return result.getValue();
	}

	protected Double[] calculateOperands(Character character) throws FormulaException {
		Double[] numbers = new Double[operands.size()];
		int i = 0;
		for (FormulaElement operand : operands) {
			numbers[i] = operand.getValue(character);
			i++;
		}
		return numbers;
	}
	
	public boolean isOperator() {
		return this instanceof OperatorElement;
	}
	
	public boolean isStatistic() {
		return this instanceof StatisticElement;
	}
	
	public boolean isBracketOrFunction() {
		return this instanceof BracketElement;
	}
}
