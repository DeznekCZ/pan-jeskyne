package cz.deznekcz.games.panjeskyne.service.formula;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.deznekcz.games.panjeskyne.i18n.I18N;

public class JavaMethodReference {
	
	private Method method;
	private String fullQualifiedName;
	private String arguments;
	private String className;
	private String argName;
	private List<Class<?>> classes;
	private String methodName;

	public JavaMethodReference(String fullQualifiedName, List<FormulaElement> parentOperands) throws FormulaException {
		this.fullQualifiedName = fullQualifiedName;
		
		check();
	}

	private void check() throws FormulaException {
		Matcher matcher = null;
		classes = new ArrayList<>(2);
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
				argName = matcher.group();
				switch (argName) {
				case "double": classes.add(double.class); break;
				case "float": classes.add(float.class); break;
				case "int": classes.add(int.class); break;
				case "short": classes.add(short.class); break;
				case "byte": classes.add(byte.class); break;
				case "long": classes.add(long.class); break;

				default: classes.add(ClassLoader.getSystemClassLoader().loadClass(argName)); break;
				}
				
			}
			methodName = fullQualifiedName.split("[(]")[0];
			methodName = methodName.substring(methodName.lastIndexOf('.')+1);
			
			method = clazz.getDeclaredMethod(
					methodName,
					(Class[]) classes.toArray(new Class[classes.size()])
					);
			
		} catch (ClassNotFoundException e) {
			throw new FormulaException(I18N.argumented(I18N.CLASS_NOT_FOUND, I18N.id(argName != null ? argName : className)));
		} catch (NoSuchMethodException e) {
			throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_FOUND, I18N.id(fullQualifiedName)));
		} catch (SecurityException e) {
			throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_VISIBLE, I18N.id(fullQualifiedName)));
		} catch (IllegalArgumentException e) {
			throw new FormulaException(I18N.argumented(I18N.METHOD_BAD_ARGUMENTS, I18N.id(arguments)));
		}
	}

	public double getValue(double[] values) throws FormulaException {
		try {
			return ((Number) method.invoke(null, (Object[]) castOperands(values))).doubleValue();
		} catch (IllegalAccessException e) {
			throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_VISIBLE, I18N.id(fullQualifiedName)));
		} catch (IllegalArgumentException e) {
			throw new FormulaException(I18N.argumented(I18N.METHOD_BAD_ARGUMENTS, I18N.id(arguments)));
		} catch (InvocationTargetException e) {
			throw new FormulaException(I18N.argumented(I18N.METHOD_NOT_INVOCATED, I18N.id(fullQualifiedName)));
		}
	}

	private Object[] castOperands(double[] input) {
		Object[] numbers = new Number[input.length];
		for (int i = 0; i < numbers.length; i++) {
			switch (classes.get(i).getSimpleName()) {
			case "double":numbers[i] = ((Number) (input[i])).doubleValue(); break;
			case "float": numbers[i] = ((Number) (input[i])).floatValue(); break;
			case "int":   numbers[i] = ((Number) (input[i])).intValue(); break;
			case "byte":  numbers[i] = ((Number) (input[i])).byteValue(); break;
			case "short": numbers[i] = ((Number) (input[i])).shortValue(); break;
			case "long":  numbers[i] = ((Number) (input[i])).longValue(); break;

			default: numbers[i] = (Number) classes.get(i).cast(input[i]); break;
			}
		}
		return numbers;
	}
}