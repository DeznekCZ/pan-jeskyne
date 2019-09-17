package cz.deznekcz.games.panjeskyne.service;

import static java.lang.Math.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cz.deznekcz.games.panjeskyne.i18n.I18N;
import cz.deznekcz.games.panjeskyne.model.xml.Table;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaException;

public class TableService {
	
	
	
	private static int[][] ZZvalue = new int[][] {
		{-4,-3,-3,-2,-2,-1, 0, 0, 1, 2, 2, 3, 4, 5, 6, 6, 7, 8, 9,10,11,12,13,14,15,15},
		{-3,-3,-2,-2,-1,-1, 0, 1, 1, 2, 3, 3, 4, 5, 6, 7, 7, 8, 9,10,11,12,13,14,15,16},
		{-3,-2,-1,-1,-1, 0, 0, 1, 2, 2, 3, 4, 4, 5, 6, 7, 8, 8, 9,10,11,12,13,14,15,16},
		{-2,-2,-1,-1, 0, 0, 1, 1, 2, 3, 3, 4, 5, 5, 6, 7, 8, 9, 9,10,11,12,13,14,15,16},
		{-2,-1,-1, 0, 0, 1, 1, 2, 2, 3, 4, 4, 5, 6, 6, 7, 8, 9,10,10,11,12,13,14,15,16},
		{-1,-1, 0, 0, 1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 7, 7, 8, 9,10,11,11,12,13,14,15,16},
		{ 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 8, 8, 9,10,11,12,12,13,14,15,16},
		{ 0, 1, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 7, 7, 8, 9, 9,10,11,12,13,13,14,15,16},
		{ 1, 1, 2, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 9,10,10,11,12,13,14,14,15,16},
		{ 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 9, 9,10,11,11,12,13,14,15,15,16},
		{ 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9,10,10,11,12,12,13,14,15,16,16},
		{ 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9,10,11,11,12,13,13,14,15,16,17},
		{ 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 9, 9,10,10,11,12,12,13,14,14,15,16,17},
		{ 5, 5, 5, 5, 6, 6, 6, 7, 7, 7, 8, 8, 9, 9,10,10,11,11,12,13,13,14,15,15,16,17},
		{ 6, 6, 6, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9,10,10,11,11,12,12,13,14,14,15,16,16,17},
		{ 6, 7, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9,10,10,11,11,12,12,13,13,14,15,15,16,17,17},
		{ 7, 7, 8, 8, 8, 8, 8, 9, 9, 9,10,10,10,11,11,12,12,13,13,14,14,15,16,16,17,18},
		{ 8, 8, 8, 9, 9, 9, 9, 9,10,10,10,11,11,11,12,12,13,13,14,14,15,15,16,17,17,18},
		{ 9, 9, 9, 9,10,10,10,10,10,11,11,11,12,12,12,13,13,14,14,15,15,16,16,17,18,18},
		{10,10,10,10,10,11,11,11,11,11,12,12,12,13,13,13,14,14,15,15,16,16,17,17,18,19},
		{11,11,11,11,11,11,12,12,12,12,12,13,13,13,14,14,14,15,15,16,16,17,17,18,18,19},
		{12,12,12,12,12,12,12,13,13,13,13,13,14,14,14,15,15,15,16,16,17,17,18,18,19,19},
		{13,13,13,13,13,13,13,13,14,14,14,14,14,15,15,15,16,16,16,17,17,18,18,19,19,20},
		{14,14,14,14,14,14,14,14,14,15,15,15,15,15,16,16,16,17,17,17,18,18,19,19,20,20},
		{15,15,15,15,15,15,15,15,15,15,16,16,16,16,16,17,17,17,18,18,18,19,19,20,20,21},
		{15,16,16,16,16,16,16,16,16,16,16,17,17,17,17,17,18,18,18,19,19,19,20,20,21,21}
	};
	
	public static double zz(double[] args) throws FormulaException
	{
		int zz = (int) args[0];
		int sila = (int) args[1];
		if (zz > 15 || sila > 15 || zz < -5 || sila < -5) {
			return round((log10(pow(2, zz / 6.0) + pow(2, sila / 6.0)) / log10(2)) * 6) - 5;
		} else {
			return ZZvalue[(zz + 5)][(sila + 5)];
		}
	}
	
	static double[] LIFEroot = v(
			1.0,1.1,1.2,1.4,1.6,1.8,2.0,2.2,2.5,2.8,
			3.2,3.6,4.0,4.5,5.0,5.6,6.3,7.0,8.0,9.0
			);
	
	static double[] LIFEext = v(
			2/6.0,2/6.0,2/6.0,3/6.0,3/6.0,3/6.0,4/6.0,4/6.0,5/6.0,5/6.0
			);
	
	public static double life(double[] args) throws FormulaException {
		int kondice = (int) args[0];
		if (kondice < -20) {
			throw new FormulaException(I18N.argumented(I18N.DATA_NOT_FOUND, I18N.id("table.life(" + kondice + ")")));
		} else if (kondice < -10) {
			return round(LIFEext[(kondice + 20)]);
		} else {
			return round(LIFEroot[(int) ((Math.pow(10, (kondice + 10) / 20)) * ((kondice + 10) % 20))]);
		}
	}
	
	private static double[] v(double...values) {
		return values;
	}
	
	private static final double[] skills = v(10, 11, 12, 14, 16, 18, 20, 22, 24);
	public static double skills(double[] args) throws FormulaException
	{
		return skills[((int) args[0])];
	}

	public static Table getTable(String identifier) throws FormulaException {
		String tableName = identifier.substring("table.".length());
		
		for (Method method : TableService.class.getMethods()) {
			if (tableName.equals(method.getName())) {
				return new Table() {
					{
						setCodename(identifier);
						
						switch (tableName) {
						case "zz": setArgsCount(2); break;
						default: setArgsCount(1); break;
						}
					}
					
					@Override
					public double getValue(double[] numbers) throws FormulaException {
						try {
							return (double) method.invoke(null, (Object) numbers);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							throw new FormulaException(I18N.argumented(I18N.DATA_NOT_FOUND, I18N.id(identifier)));
						}
					}
				};
			}
		}
		throw new FormulaException(I18N.argumented(I18N.TABLE_NOT_FOUND, I18N.id(identifier)));
	}
}
