package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.function.Function;

public interface XmlMappable<K, V> extends XmlSerialized {
	
	public K getKey();
	
	public V getValue();
}
