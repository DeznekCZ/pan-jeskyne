package cz.deznekcz.games.panjeskyne.data;

public class BadClassException extends Exception {

	private static final long serialVersionUID = -6489185911168808951L;
	
	private Class<?> instanciatedClass;
	private Exception exception;

	public BadClassException(Class<?> instance, Exception exception) {
		this.instanciatedClass = instance == null ? Object.class : instance;
		this.exception = exception;
	}

	@Override
	public String getLocalizedMessage() {
		return "Class: " + instanciatedClass.getSimpleName() + " do can not be used becaouse: " + exception.getLocalizedMessage();
	}
}
