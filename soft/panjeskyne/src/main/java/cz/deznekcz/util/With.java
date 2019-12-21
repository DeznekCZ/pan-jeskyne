package cz.deznekcz.util;

import java.util.function.Consumer;

public class With<T> {

	private T t;

	private With(T t) {
		this.t = t;
	}

	public static <T> With<T> that(T t) {
		return new With<T>(t);
	}

	public With<T> apply(Consumer<T> action) {
		action.accept(t);
		return this;
	}

}
