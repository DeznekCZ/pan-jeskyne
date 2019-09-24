package cz.deznekcz.games.panjeskyne.model.xml.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cz.deznekcz.games.panjeskyne.model.xml.ListType;
import cz.deznekcz.games.panjeskyne.model.xml.XmlMappable;

public abstract class SimpleMapAdapter<V> 
		extends ListToMapAdapter<
					java.lang.String, 
					V, 
					SimpleMapAdapter.StringSimpleList<V>,
					SimpleMapAdapter.StringSimpleEntry<V>> {

	public static class Integer extends SimpleMapAdapter<java.lang.Integer> {}
	public static class Double extends SimpleMapAdapter<java.lang.Double> {}
	public static class String extends SimpleMapAdapter<java.lang.String> {}

	@XmlRootElement(name = "entry")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class StringSimpleEntry<V> implements XmlMappable<java.lang.String, V> {

		private static final long serialVersionUID = -3835770974231860914L;

		@XmlAttribute(name = "key")
		private java.lang.String key;
		
		@XmlAttribute(name = "value")
		private V value;

		@Override
		public java.lang.String getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class StringSimpleList<V> implements ListType<SimpleMapAdapter.StringSimpleEntry<V>> {

		private static final long serialVersionUID = -1200032323315016179L;
		
		@XmlElement(name = "entry")
		private List<SimpleMapAdapter.StringSimpleEntry<V>> list;

		@Override
		public List<SimpleMapAdapter.StringSimpleEntry<V>> getList() {
			return list;
		}

		@Override
		public void setList(List<StringSimpleEntry<V>> list) {
			this.list = list;
		}
	}

	@Override
	public StringSimpleList<V> marshal(HashMap<java.lang.String, V> v) throws Exception {
		StringSimpleList<V> sil = new StringSimpleList<>();
		sil.list = new ArrayList<>();
		for (Entry<java.lang.String, V> set : v.entrySet()) {
			StringSimpleEntry<V> entry = new StringSimpleEntry<>();
			entry.key = set.getKey();
			entry.value = set.getValue();
			sil.list.add(entry);
		}
		return sil;
	}

}
