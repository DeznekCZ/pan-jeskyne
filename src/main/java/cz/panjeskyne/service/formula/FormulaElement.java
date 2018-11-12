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
		private Method method;
		private String fullQualifiedName;
		private String arguments;
		private String className;

		public MathElement(FunctionElement element) throws FormulaException {
			this.element = element;
			check();
		}

		private void check() throws FormulaException {
			// java.lang.Math.abs(java.lang.Double)
			fullQualifiedName = this.element.function.getFormula();
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
				method = clazz.getMethod(
						fullQualifiedName.substring(fullQualifiedName.split("[(]")[0].lastIndexOf('.')+1),
						classes.toArray(new Class[classes.size()])
						);
				
				
			} catch (ClassNotFoundException e) {
				throw new FormulaException(I18N.argumented(I18N.CLASS_NOT_FOUND, I18N.id(className)));
			} catch (NoSuchMethodException e) {
				throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_FOUND, I18N.id(fullQualifiedName)));
			} catch (SecurityException e) {
				throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_VISIBLE, I18N.id(fullQualifiedName)));
			} catch (IllegalArgumentException e) {
				throw new FormulaException(I18N.argumented(I18N.METHOD_BAD_ARGUMENTS, I18N.id(arguments)));
			}
		}

		@Override
		public Double getValue(Character character) throws FormulaException {
			try {
				return (Double) method.invoke(null, (Object[]) calculateOperands(character));
			} catch (IllegalAccessException e) {
				throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_VISIBLE, I18N.id(fullQualifiedName)));
			} catch (IllegalArgumentException e) {
				throw new FormulaException(I18N.argumented(I18N.METHOD_BAD_ARGUMENTS, I18N.id(arguments)));
			} catch (InvocationTargetException e) {
				throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_INVOCATED, I18N.id(fullQualifiedName)));
			}
		}

		@Override
		protected FormulaElement applyChild(FormulaElement child) {
			child.parent = this;
			this.operands.add(child);
			return child;
		}

		@Override
		public String toString() {
			return className + ".(" + toStringOperands() + ")";
		}
	}
	
	public static class OperatorElement extends FormulaElement {

		private OperandType operandType;

		public OperatorElement(OperandType operatorType) {
			this.operandType = operatorType;
		}

		@Override
		public Double getValue(Character character) throws FormulaException {
			return operandType.apply(calculateOperands(character));
		}

		@Override
		protected FormulaElement applyChild(FormulaElement child) throws FormulaException {
			if (operandType.getOperands() > operands.size()) {
				child.parent = this;
				operands.add(child);
			} else if (child.isOperator()) {
				if (operandType.getPriority() > cast(child).operandType.getPriority()) {
					child.operands.add(this);
					child.parent = this.parent;
					this.parent = this;
				} else {
					child.parent = this;
					child.operands.add(this.operands.get(1));
					this.operands.get(1).parent = child;
					this.operands.set(1, child);
				}
			} else {
				throw new FormulaException(I18N.argumented(I18N.TOO_MUCH_OPERANDS, I18N.id(getClass().getName())));
			}
			return child;
		}

		private OperatorElement cast(FormulaElement child) {
			return (OperatorElement) child;
		}

		@Override
		public String toString() {
			return operandType.toString(operands);
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

		@Override
		protected FormulaElement applyChild(FormulaElement child) throws FormulaException {
			if (child.isSimpleType()) {
				throw new FormulaException(I18N.argumented(I18N.CHILDREN_NOT_IMPLEMENTED, I18N.id(getClass().getName())));
			} else if (!child.isSimpleType() && !hasParent()) {
				child.applyChild(this);
				this.parent = child;
				return child;
			} else {
				throw new FormulaException(I18N.HAS_PARENT_ELEMENT);
			}
		}
		
		@Override
		public String toString() {
			return statistic.getCodename() != null ? statistic.getCodename() : "[NOT_DEFINED]";
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

		@Override
		protected FormulaElement applyChild(FormulaElement child) throws FormulaException {
			if (child.isSimpleType()) {
				throw new FormulaException(I18N.argumented(I18N.CHILDREN_NOT_IMPLEMENTED, I18N.id(getClass().getName())));
			} else if (!child.isSimpleType() && !hasParent()) {
				child.applyChild(this);
				this.parent = child;
				return child;
			} else if (hasParent()) {
				getParent().applyChild(child);
				return child;
			} else {
				throw new FormulaException(I18N.HAS_PARENT_ELEMENT);
			}
		}
		
		@Override
		public String toString() {
			return number != null ? String.valueOf(number) : "[NOT_DEFINED]";
		}

	}

	public static class BracketElement extends FormulaElement {

		private boolean closed;

		public BracketElement() {
			this.closed = false;
		}
		
		@Override
		public Double getValue(Character character) throws FormulaException {
			if (operands.size() != 1)
				throw new FormulaException(I18N.BRACKET_INVALID);
			return operands.get(0).getValue(character);
		}
		
		public void setClosed(boolean closed) {
			this.closed = closed;
		}

		public boolean isClosed() {
			return closed;
		}

		@Override
		protected FormulaElement applyChild(FormulaElement child) throws FormulaException {
			if (isClosed()) {
				throw new FormulaException(I18N.BRACKET_IS_CLOSED);
			} else if (operands.size() < 1) {
				this.operands.add(child);
				child.parent = this;
			} else if (operands.size() == 1 && !child.isSimpleType()) {
				this.operands.get(0).parent = null;
				child.applyChild(this.operands.get(0));
				this.operands.set(0, child);
				child.parent = this;
			} else {
				throw new FormulaException(I18N.argumented(I18N.TOO_MUCH_OPERANDS, I18N.id(getClass().getName())));
			}
			return child;
		}

		@Override
		public String toString() {
			return operands.size() > 0 ? ("("+operands.get(0).toString()+")") : ("([NOT_DEFINED])");
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
			} else if (function.getType() == FunctionTypes.MATH) {
				result = new Result();
				result.setFormula(new Formula(function.getFormula()));
				result.getFormula().setRootElement(new FormulaElement.MathElement(this));
			} else {
				throw new FormulaException(I18N.argumented(I18N.FUNCTION_NOT_FOUND, I18N.id(identifier)));
			}
		}

		@Override
		public Double getValue(Character character) throws FormulaException {
			if (function.getType() == FunctionTypes.TABLE) {
				if (operands.size() != table.getArgsCount()) {
					throw new FormulaException(I18N.argumented(I18N.TABLE_INVALID_ARGS_COUNT, 
							I18N.string(table.getCodename()),
							I18N.number(table.getArgsCount()), 
							I18N.number(operands.size())));
				}
				return table.getValue(calculateOperands(character));
			}
			if (function.getType() == FunctionTypes.MATH) {
				result.applyFormula(character);
				return result.getValue();
			}
			return 0.0;
		}
		
		@Override
		protected FormulaElement applyChild(FormulaElement child) throws FormulaException {
			if (isClosed()) {
				throw new FormulaException(I18N.BRACKET_IS_CLOSED);
			} else if (function.getType() == FunctionTypes.MATH) {
				return result.getFormula().getRootElement().applyChild(child);
			} else if (function.getType() == FunctionTypes.TABLE) {
				if (this.operands.size() < table.getArgsCount()) {
					this.operands.add(child);
					child.parent = this;
					return child;
				} else {
					throw new FormulaException(I18N.argumented(I18N.TOO_MUCH_OPERANDS, I18N.id(getClass().getName())));
				}
			}
			return child;
		}

		@Override
		public String toString() {
			if (function.getType() == FunctionTypes.MATH)
				return result.getFormula().getRootElement().toString();
			if (function.getType() == FunctionTypes.TABLE)
				return operands.size() > 0 ? (identifier+"("+toStringOperands()+")") : (identifier+"([NOT_DEFINED])");
			return "[NOT_DEFINED]";
		}
	}

	public static FormulaElement function(String codename) throws FormulaException {
		return new FunctionElement(codename);
	}

	public String toStringOperands() {
		StringBuffer sb = new StringBuffer();
		
		for (FormulaElement formulaElement : operands) {
			sb.append(",");
			sb.append(formulaElement.toString());
		}
		
		return sb.toString().substring(2);
	}

	public static FormulaElement variable(String codename) throws FormulaException {
		Statistic statistic = StatisticService.getStatistic(codename);
		if (statistic == null) throw new FormulaException(I18N.argumented(I18N.DATA_NOT_FOUND, I18N.id(codename)));
		return new StatisticElement(statistic);
	}

	public static FormulaElement bracket() throws FormulaException {
		return new BracketElement();
	}
	
	public static FormulaElement number(Double number) throws FormulaException {
		return new NumberElement(number);
	}

	protected List<FormulaElement> operands = new ArrayList<>(2);
	protected FormulaElement parent;

	public boolean isSimpleType() {
		return this instanceof NumberElement || this instanceof StatisticElement;
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

	public boolean applyClose() {
		if (this.isCloseAble()) {
			((BracketElement) this).setClosed(true);
			return true;
			
		} else {
			return hasParent() && getParent().applyClose();
		}
	}

	public FormulaElement getParent() {
		return parent;
	}

	protected boolean hasParent() {
		return parent != null;
	}

	public FormulaElement setParent(FormulaElement parent) throws FormulaException {
		if (parent != null) {
			return parent.applyChild(this);
		} else {
			return this;
		}
	}
	
	protected abstract FormulaElement applyChild(FormulaElement child) throws FormulaException;

	public boolean isCloseAble() {
		return this instanceof BracketElement && !((BracketElement) this).isClosed();
	}

	public static FormulaElement operator(OperandType type) {
		return new OperatorElement(type);
	}
}
