package cz.deznekcz.games.panjeskyne.model.xml;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

public interface XmlSerialized extends Serializable {

	int EMPTY = 0;
	int USED = 0;

	default void writeObject(ObjectOutputStream out) throws IOException {
		Field[] fields = this.getClass().getEnclosingClass().getFields();
		
		HashMap<String, Object> data = new HashMap<>();
		if (fields != null) {
			for (Field field : fields) {
				boolean acc = field.isAccessible();
				field.setAccessible(true);
				try {
					data.put(field.getName(), field.get(this));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				field.setAccessible(acc);
			}
		}
		out.writeObject(data);
	}
	
	@SuppressWarnings("unchecked")
	default void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		Field[] fields = this.getClass().getEnclosingClass().getFields();
		
		HashMap<String, Object> data = (HashMap<String, Object>) in.readObject();
		if (fields != null) {
			for (Field field : fields) {
				if (data.containsKey(field.getName())) {
					boolean acc = field.isAccessible();
					field.setAccessible(true);
					try {
						field.set(this, data.get(field.getName()));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
					field.setAccessible(acc);
				}
			}
		}
	}
	
	default void readObjectNoData() throws ObjectStreamException {
		throw new ObjectStreamException() {
			private static final long serialVersionUID = 1L;
		};
	}

}
