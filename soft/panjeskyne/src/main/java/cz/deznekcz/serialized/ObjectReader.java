package cz.deznekcz.serialized;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectReader {

	private ObjectInputStream in;

	public ObjectReader(ObjectInputStream in) {
		this.in = in;
	}

	@SuppressWarnings("unchecked")
	public <T> T read() throws ClassCastException, ClassNotFoundException, IOException {
		return (T) in.readObject();
	}
}
