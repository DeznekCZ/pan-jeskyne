package cz.deznekcz.serialized;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectWriter {

	ObjectOutputStream os;
	
	public ObjectWriter(ObjectOutputStream os) {
		super();
		this.os = os;
	}

	public <T> void add(T object) throws IOException {
		os.writeObject(object);
	}

}
