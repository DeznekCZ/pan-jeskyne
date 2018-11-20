package cz.panjeskyne.service.impl;

import java.util.HashMap;

import cz.panjeskyne.model.db.Function;
import cz.panjeskyne.service.FunctionService;
import cz.panjeskyne.service.formula.FunctionTypes;

public class FunctionServiceImpl implements FunctionService {

	static HashMap<String, Function> map = new HashMap<>();
	
	static {
		buildFunction("function.abs", 1, "java.lang.Math.abs(double)");
		buildFunction("function.rounddown", 1, "java.lang.Math.floor(double)");
		buildFunction("function.roundup", 1, "java.lang.Math.ceil(double)");
		buildFunction("function.round", 1, "java.lang.Math.round(double)");
		buildFunction("function.abs_i", 1, "java.lang.Math.abs(int)");
	}

	static void buildFunction(String codename, int args, String formula) {
		Function f = new Function();
		f.setCodename(codename);
		f.setType(FunctionTypes.MATH);
		f.setFormula(formula);
		f.setArgsCount(args);
		map.put(codename, f);
	}

	public static Function getFunction(String identifier) {
		return map.get(identifier);
	}
}
