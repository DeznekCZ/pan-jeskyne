package cz.deznekcz.games.panjeskyne.service;

import java.util.HashMap;

import cz.deznekcz.games.panjeskyne.model.xml.Function;
import cz.deznekcz.games.panjeskyne.service.FunctionService;
import cz.deznekcz.games.panjeskyne.service.formula.FunctionTypes;

public class FunctionService {

	static HashMap<String, Function> map = new HashMap<>();
	
	static {
		buildFunction("function.abs", 1, "java.lang.Math.abs(double)");
		buildFunction("function.floor", 1, "java.lang.Math.floor(double)");
		buildFunction("function.ceil", 1, "java.lang.Math.ceil(double)");
		buildFunction("function.rounddown", 1, "java.lang.Math.floor(double)");
		buildFunction("function.roundup", 1, "java.lang.Math.ceil(double)");
		buildFunction("function.round", 1, "java.lang.Math.round(double)");
		buildFunction("function.abs_i", 1, "java.lang.Math.abs(int)");
		buildFunction("function.min", 2, "java.lang.Math.min(double,double)");
		buildFunction("function.max", 2, "java.lang.Math.max(double,double)");

		buildTable("table.zz");
		buildTable("table.life");
		buildTable("table.skills");
	}

	static void buildFunction(String codename, int args, String formula) {
		Function f = new Function();
		f.setCodename(codename);
		f.setType(FunctionTypes.MATH);
		f.setFormula(formula);
		f.setArgsCount(args);
		map.put(codename, f);
	}

	private static void buildTable(String codename) {
		Function f = new Function();
		f.setCodename(codename);
		f.setType(FunctionTypes.TABLE);
		map.put(codename, f);
	}

	public static Function getFunction(String identifier) {
		return map.get(identifier);
	}
}
