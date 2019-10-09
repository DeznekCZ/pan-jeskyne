package cz.deznekcz.serialized;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public interface Serialized extends Serializable {

	void store(ObjectWriter writer) throws IOException;
	
	void load(ObjectReader reader) throws IOException, ClassNotFoundException;
	
	default void writeObject(java.io.ObjectOutputStream out) throws IOException {
		store(new ObjectWriter(out));
	}
	
	default void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException, ClassCastException {
		load(new ObjectReader(in));
	}
	
	default void readObjectNoData() throws ObjectStreamException {
		
	}
}
