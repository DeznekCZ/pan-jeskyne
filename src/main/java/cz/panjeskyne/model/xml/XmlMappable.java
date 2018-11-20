package cz.panjeskyne.model.xml;

public interface XmlMappable<K, V> {
	
	public K getKey();
	
	public V getValue();
}
