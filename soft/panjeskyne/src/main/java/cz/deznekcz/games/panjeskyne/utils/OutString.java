package cz.deznekcz.games.panjeskyne.utils;

import java.io.IOException;

public class OutString implements Appendable {

	private String string;

	public OutString(String string) {
		this.string = string;
	}

	public static OutString init() {
		return new OutString("");
	}

	public String get() {
		return string;
	}

	@Override
	public Appendable append(CharSequence csq) throws IOException {
		string += (csq instanceof OutString ? ((OutString) csq).get() : csq.toString());
		return this;
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) throws IOException {
		string += (csq instanceof OutString ? ((OutString) csq).get() : csq.toString()).subSequence(start, end);
		return this;
	}

	@Override
	public Appendable append(char c) throws IOException {
		string += c;
		return this;
	}

	public void set(String string) {
		this.string = string;
	}
}
