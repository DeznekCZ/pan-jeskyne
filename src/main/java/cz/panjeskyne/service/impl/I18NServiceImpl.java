package cz.panjeskyne.service.impl;

import java.util.HashMap;

public class I18NServiceImpl {

	private static final HashMap<String,String> map = new HashMap<>();

	static {
		add("exception.table.not_found", "Tabulka s id: \"%s\" nenalezena");
		add("exception.table.invalid_args_count", "Tabulka \"%s\" oèekává tento poèet argumentù: %s obdržela však %s");
		add("exception.formula.bracket_too_much_arguments", "V zavorkách nesmí být znak ','");
		add("exception.formula.operator_invalid_count_operands", "Není zadán správný poèet operandù pro operátor '%s', oèekáváno: %s obdrženo: %s");
		add("exception.function.not_found", "Zadaná funkce neexistuje (není znám typ): %s");
		add("exception.class.not_found", "Zadaná tøída nenalezena: %s");
		add("exception.method.not_found", "Zadaná metoda nenalezena: %s");
		add("exception.method.not_visible", "Zadaná metoda není pøístupná: %s");
		add("exception.function.format_exception", "Neodpovídá standartnímu formátu: %s");
		add("exception.method.bad_arguments", "Chybné doruèení argumenty: oèekáváno %s"); 
		add("exception.method.bad_invocation", "Nelze volat metodu: %s");
	}
	
	public static String getString(String codename) {
		return map.get(codename);
	}

	private static void add(String codename, String value) {
		map.put(codename, value);
	}

}
