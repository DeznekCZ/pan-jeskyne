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
		add("exception.method.bad_arguments", "Chybné doruèení argumentù: oèekáváno %s"); 
		add("exception.method.bad_invocation", "Nelze volat metodu: %s");
		add("exception.formula.missing_opening_bracket", "Chybìjící otevírací závorka");
		add("exception.formula.child_elements_not_implemented", "Aktuální element \"%s\" nepodporuje subelementy");
		add("exception.formula.bracket_is_closed", "Závorka je již uzavøena.");
		add("exception.formula.too_much_operands", "Aktuální element \"%s\" nepodporuje více operandù");
		add("exception.formula.has_no_parent", "Chybìjící rodièovský element");
		add("exception.data.not_found", "Nenalezena data pro id: %s");
		add("exception.function.invalid_count_arguments", "Funkce \"%s\" oèekává tento poèet argumentù: %s obdržela však %s");
		
	}
	
	public static String getString(String codename) {
		return map.get(codename);
	}

	private static void add(String codename, String value) {
		map.put(codename, value);
	}

	public static String getString(String codename, String defaultText) {
		return map.getOrDefault(codename, defaultText);
	}

}
