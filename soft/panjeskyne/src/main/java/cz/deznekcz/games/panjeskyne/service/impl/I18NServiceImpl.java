package cz.deznekcz.games.panjeskyne.service.impl;

import java.util.HashMap;

public class I18NServiceImpl {

	private static final HashMap<String,String> map = new HashMap<>();

	static {
		add("exception.table.not_found", "Tabulka s id: \"%s\" nenalezena");
		add("exception.table.invalid_args_count", "Tabulka \"%s\" očekává tento počet argumentů: %s obdržela však %s");
		add("exception.formula.bracket_too_much_arguments", "V zavorkách nesmí být znak ','");
		add("exception.formula.operator_invalid_count_operands", "Není zadán správný počet operandů pro operátor '%s', očekáváno: %s obdrženo: %s");
		add("exception.function.not_found", "Zadaná funkce neexistuje (není znám typ): %s");
		add("exception.class.not_found", "Zadaná třída nenalezena: %s");
		add("exception.method.not_found", "Zadaná metoda nenalezena: %s");
		add("exception.method.not_visible", "Zadaná metoda není přístupná: %s");
		add("exception.function.format_exception", "Neodpovídá standartnímu formátu: %s");
		add("exception.method.bad_arguments", "Chybně doručené argumenty: očekáváno %s"); 
		add("exception.method.bad_invocation", "Nelze volat metodu: %s");
		add("exception.formula.missing_opening_bracket", "Chybějící otevírací závorka");
		add("exception.formula.child_elements_not_implemented", "Aktuálně element \"%s\" nepodporuje subelementy");
		add("exception.formula.bracket_is_closed", "Závorka je již uzavřena.");
		add("exception.formula.too_much_operands", "Aktuálně element \"%s\" nepodporuje více operandů");
		add("exception.formula.has_no_parent", "Chybějící rodičovský element");
		add("exception.data.not_found", "Nenalezena data pro id: %s");
		add("exception.function.invalid_count_arguments", "Funkce \"%s\" očekávý tento počet argumentů: %s obdržela však %s");	
		
		add("tabs.rules", "Pravidla");	
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
