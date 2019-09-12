package cz.deznekcz.games.panjeskyne.model.xml.adapter;

import java.util.HashMap;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import cz.deznekcz.games.panjeskyne.model.xml.ListType;
import cz.deznekcz.games.panjeskyne.model.xml.XmlMappable;

public class ListToMapAdapter<K, V, LT extends ListType<? extends XmlMappable<K, V>>>
		extends XmlAdapter<LT, HashMap<K, V>> {

	@Override
	public HashMap<K, V> unmarshal(LT v) throws Exception {
		HashMap<K, V> result = new HashMap<>();
		for (XmlMappable<K, V> vt : v.getList()) {
			result.put(vt.getKey(), vt.getValue());
		}
		return result;
	}

	@Override
	public LT marshal(HashMap<K, V> v) throws Exception {
		// Neni potreba
		throw new UnsupportedOperationException();
	}

}
