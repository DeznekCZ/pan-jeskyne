package cz.deznekcz.games.panjeskyne.model.xml;

public interface XmlMappable<K, V> extends XmlSerialized {
	
	public K getKey();
	
	public V getValue();
}
