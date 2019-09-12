package cz.deznekcz.games.panjeskyne.utils;

public class Out<R> {

	private R ref;

	public Out(R string) {
		this.ref = string;
	}

	public static <R> Out<R> init() {
		return new Out<R>(null);
	}

	public R get() {
		return ref;
	}

	public boolean isNull() {
		return ref == null;
	}

	public void set(R ref) {
		this.ref = ref;
	}
}
