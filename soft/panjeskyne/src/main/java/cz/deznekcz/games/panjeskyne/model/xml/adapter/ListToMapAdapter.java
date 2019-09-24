package cz.deznekcz.games.panjeskyne.model.xml.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import cz.deznekcz.games.panjeskyne.model.xml.ListType;
import cz.deznekcz.games.panjeskyne.model.xml.XmlMappable;

public abstract class ListToMapAdapter<K, V, LT extends ListType<M>, M extends XmlMappable<K, V>>
		extends XmlAdapter<LT, HashMap<K, V>> {

	public LT build(Supplier<LT> ltConstructor, Map<K, M> data) {
		LT lt = ltConstructor.get();
		List<M> list = new ArrayList<>();
		for (Entry<K, M> m : data.entrySet()) {
			list.add(m.getValue());
		}
		lt.setList(list);
		return lt;
	}
	
	@Override
	public HashMap<K, V> unmarshal(LT v) throws Exception {
		HashMap<K, V> result = new HashMap<>();
		for (XmlMappable<K, V> vt : v.getList()) {
			result.put(vt.getKey(), vt.getValue());
		}
		return result;
	}

	@Override
	public abstract LT marshal(HashMap<K, V> v) throws Exception;

}
