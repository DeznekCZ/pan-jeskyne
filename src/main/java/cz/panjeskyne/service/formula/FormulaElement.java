package cz.panjeskyne.service.formula;

import java.util.ArrayList;
import java.util.List;

import cz.panjeskyne.i18n.I18N;
import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.model.db.Function;
import cz.panjeskyne.model.db.Table;
import cz.panjeskyne.model.xml.Statistic;
import cz.panjeskyne.service.FunctionService;
import cz.panjeskyne.service.Result;
import cz.panjeskyne.service.TableService;
import cz.panjeskyne.service.providers.StatisticProvider;

public abstract class FormulaElement {

	public static class NextElement extends FormulaElement {

		@Override
		public double getValue(Character character) throws FormulaException {
			return 0;
		}

		@Override
		protected FormulaElement applyChild(FormulaElement child) throws FormulaException {
			return null;
		}

	}
	
	public static class OperatorElement extends FormulaElement {

		private OperandType operandType;

		public OperatorElement(OperandType operatorType) {
			this.operandType = operatorType;
		}

		@Override
		public double getValue(Character character) throws FormulaException {
			return operandType.apply(calculateOperands(character));
		}

		@Override
		protected FormulaElement applyChild(FormulaElement child) throws FormulaException {
			if (operandType.getOperands() > operands.size() && !child.isOperator()) {
				child.parent = this;
				operands.add(child);
			} else if (child.isOperator()) {
				if (operandType.getPriority() > cast(child).operandType.getPriority()) {
					child.operands.add(this);
					child.parent = this.parent;
					this.parent = child;
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
		public double getValue(Character character) throws FormulaException {
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
		public double getValue(Character character) throws FormulaException {
			return number;
		}

		@Override
		protected FormulaElement applyChild(FormulaElement child) throws FormulaException {
			if (child.isSimpleType() && !hasParent()) {
				throw new FormulaException(I18N.argumented(I18N.CHILDREN_NOT_IMPLEMENTED, I18N.id(getClass().getName())));
			} else if (!child.isSimpleType() && !hasParent()) {
				return child.applyChild(this);
			} else if (hasParent()) {
				return getParent().applyChild(child);
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
		public double getValue(Character character) throws FormulaException {
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
			if (isClosed() && child.isSimpleType()) {
				throw new FormulaException(I18N.BRACKET_IS_CLOSED);
			} else if (operands.size() < 1 && !isClosed()) {
				this.operands.add(child);
				child.parent = this;
			} else if (operands.size() == 1 && !child.isSimpleType() && !isClosed() && !child.isNext()) {
				FormulaElement tmp = this.operands.get(0);
				this.operands.set(0, child);
				child.parent = this;
				child.applyChild(tmp);
			} else if ((isClosed() || child.isNext()) && !child.isSimpleType()) {
				if (hasParent()) {
					return getParent().applyChild(child);
				} else {
					child.applyChild(this);
				}
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
		private JavaMethodReference math;
		private BracketElement argument;
		private int nextArgument;
		private int argsCount;

		public FunctionElement(String identifier) throws FormulaException {
			this.identifier = identifier;
			
			check();
		}

		private void check() throws FormulaException {
			function = FunctionService.getFunction(identifier);
			if (function == null) {
				throw new FormulaException(I18N.argumented(I18N.FUNCTION_NOT_FOUND, I18N.id(identifier)));
			} else if (function.getType() == FunctionTypes.TABLE) {
				table = TableService.getTable(identifier);
				if (table == null) throw new FormulaException(I18N.argumented(I18N.TABLE_NOT_FOUND, I18N.id(identifier)));
				for (int i = 0; i < table.getArgsCount(); i++) {
					this.operands.add(FormulaElement.bracket());
					this.operands.get(i).parent = this;
				}
				this.argument = (BracketElement) this.operands.get(0);
				this.nextArgument = 1;
				this.setArgsCount(table.getArgsCount());
				
			} else if (function.getType() == FunctionTypes.MATH) {
				this.math = new JavaMethodReference(this.function.getFormula(), operands);
				for (int i = 0; i < function.getArgsCount(); i++) {
					this.operands.add(FormulaElement.bracket());
					this.operands.get(i).parent = this;
				}
				this.argument = (BracketElement) this.operands.get(0);
				this.nextArgument = 1;
				this.setArgsCount(function.getArgsCount());
			} else {
				throw new FormulaException(I18N.argumented(I18N.FUNCTION_NOT_FOUND, I18N.id(identifier)));
			}
		}

		public void setArgsCount(int argsCount) {
			this.argsCount = argsCount;
		}

		public int getArgsCount() {
			return argsCount;
		}
		
		@Override
		public double getValue(Character character) throws FormulaException {
			if (function.getType() == FunctionTypes.TABLE) {
				return table.getValue(calculateOperands(character));
			}
			if (function.getType() == FunctionTypes.MATH) {
				return math.getValue(calculateOperands(character));
			}
			return 0.0;
		}
		
		@Override
		protected FormulaElement applyChild(FormulaElement child) throws FormulaException {
			if (isClosed()) {
				throw new FormulaException(I18N.BRACKET_IS_CLOSED);
			} else if (child instanceof NextElement) {
				if (nextArgument == operands.size()) {
					throw new FormulaException(I18N.argumented(I18N.TOO_MUCH_OPERANDS, I18N.id(getClass().getName())));
				} else {
					argument.applyClose();
					argument = (BracketElement) operands.get(nextArgument);
					nextArgument ++;
				}
				return this;
			} else {
				return argument.applyChild(child);
			}
		}

		@Override
		public String toString() {
			return operands.size() > 0 ? (identifier+"("+toStringOperands()+")") : (identifier+"([NOT_DEFINED])");
		}
		
		@Override
		public FormulaElement applyClose() throws FormulaException {
			if (nextArgument < operands.size()) {
				throw new FormulaException(
						I18N.argumented(I18N.INVALID_ARGS_COUNT, 
								I18N.id(identifier), I18N.number(nextArgument), I18N.number(getArgsCount())));
			} else {
				argument.applyClose();
			}
			return super.applyClose();
		}
	}

	public static FormulaElement function(String codename) throws FormulaException {
		return new FunctionElement(codename);
	}

	public boolean isNext() {
		return this instanceof NextElement;
	}

	public String toStringOperands() {
		StringBuffer sb = new StringBuffer();
		
		for (FormulaElement formulaElement : operands) {
			sb.append(",");
			sb.append(formulaElement.toString());
		}
		
		return sb.toString().substring(1);
	}

	public static FormulaElement variable(String codename) throws FormulaException {
		Statistic statistic = new Statistic();// .getStatistic(codename);
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

	public abstract double getValue(Character character) throws FormulaException;
	
	public Double validate(Result result) throws FormulaException {
		if (!result.isSuccessful()) throw result.getException();
		return result.getValue();
	}

	protected double[] calculateOperands(Character character) throws FormulaException {
		double[] numbers = new double[operands.size()];
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

	public FormulaElement applyClose() throws FormulaException {
		if (this.isCloseAble()) {
			((BracketElement) this).setClosed(true);
			return this;
			
		} else {
			if (hasParent()) {
				return getParent().applyClose();
			} else {
				throw new FormulaException(I18N.MISSING_OPENING_BRACKET);
			}
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

	public static FormulaElement next() {
		return new NextElement();
	}
}
