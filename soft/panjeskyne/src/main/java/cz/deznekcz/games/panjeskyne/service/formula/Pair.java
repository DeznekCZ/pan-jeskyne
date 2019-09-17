package cz.deznekcz.games.panjeskyne.service.formula;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Pair<K, V> implements Serializable {

	private static final long serialVersionUID = 5525806260784266240L;
	private K key;
	private V value;

	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public void setKey(K key) {
		this.key = key;
	}
	
	public void setValue(V value) {
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(key);
		out.writeObject(value);
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		key = (K) in.readObject();
		value = (V) in.readObject();
	}

}
