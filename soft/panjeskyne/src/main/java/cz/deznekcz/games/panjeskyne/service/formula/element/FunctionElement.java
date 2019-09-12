package cz.deznekcz.games.panjeskyne.service.formula.element;

import cz.deznekcz.games.panjeskyne.i18n.I18N;
import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Function;
import cz.deznekcz.games.panjeskyne.model.xml.Table;
import cz.deznekcz.games.panjeskyne.service.FunctionService;
import cz.deznekcz.games.panjeskyne.service.StatisticService;
import cz.deznekcz.games.panjeskyne.service.TableService;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaElement;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaException;
import cz.deznekcz.games.panjeskyne.service.formula.FunctionTypes;
import cz.deznekcz.games.panjeskyne.service.formula.JavaMethodReference;

public class FunctionElement extends BracketElement {

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
	public double getValue(StatisticService provider, Character character) throws FormulaException {
		if (function.getType() == FunctionTypes.TABLE) {
			return table.getValue(calculateOperands(provider, character));
		}
		if (function.getType() == FunctionTypes.MATH) {
			return math.getValue(calculateOperands(provider, character));
		}
		return 0.0;
	}
	
	@Override
	public FormulaElement applyChild(FormulaElement child) throws FormulaException {
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
