package cz.deznekcz.games.panjeskyne.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.collect.Maps;

import cz.deznekcz.games.panjeskyne.data.object.DoubleData;
import cz.deznekcz.games.panjeskyne.data.object.IntegerData;
import cz.deznekcz.games.panjeskyne.data.object.ObjectData;
import cz.deznekcz.games.panjeskyne.data.object.StringData;
import cz.deznekcz.games.panjeskyne.model.xml.ListType;
import cz.deznekcz.games.panjeskyne.model.xml.XmlMappable;
import cz.deznekcz.games.panjeskyne.model.xml.adapter.ListToMapAdapter;
import cz.deznekcz.games.panjeskyne.model.xml.adapter.SimpleMapAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterRulesData implements XmlMappable<String, CharacterRulesData> {
	
	private static final long serialVersionUID = -7582472214541649631L;

	public static class Adapter extends ListToMapAdapter<String, CharacterRulesData, CRDList, CharacterRulesData> {

		@Override
		public CRDList marshal(HashMap<String, CharacterRulesData> v) throws Exception {
			return build(CRDList::new, v);
		}

	}
	
	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class CRDList implements ListType<CharacterRulesData> {

		private static final long serialVersionUID = -3068515839199733202L;
		
		@XmlElement(name = "rules")
		private List<CharacterRulesData> list;

		@Override
		public List<CharacterRulesData> getList() {
			return list;
		}

		@Override
		public void setList(List<CharacterRulesData> list) {
			this.list = list;
		}
	}

	@XmlAttribute(name = "id")
	private String id;
	

	@Override
	public String getKey() {
		return id;
	}

	@Override
	public CharacterRulesData getValue() {
		return this;
	}
	
	public void init(String id) {
		this.id = id;
		integers = Maps.newHashMap();
//		strings = Maps.newHashMap();
//		doubles = Maps.newHashMap(); 
//		skills = Maps.newHashMap();
//		items = Maps.newHashMap();
	}
	
	@XmlJavaTypeAdapter(value = IntegerData.Adapter.class)
	@XmlElement(name = "integers")
	private HashMap<String, IntegerData> integers;
	
	@XmlJavaTypeAdapter(value = StringData.Adapter.class)
	@XmlElement(name = "strings")
	private HashMap<String, StringData> strings;
	
	@XmlJavaTypeAdapter(value = DoubleData.Adapter.class)
	@XmlElement(name = "doubles")
	private HashMap<String, DoubleData> doubles;
	
//	@XmlJavaTypeAdapter(value = SkillData.Adapter.class)
//	@XmlElement(name = "skills")
//	private HashMap<String, SkillData> skills;
//	
//	@XmlJavaTypeAdapter(value = ItemData.Adapter.class)
//	@XmlElement(name = "items")
//	private HashMap<String, ItemData> items;

	public HashMap<String, IntegerData> getIntegers() {
		return integers;
	}
	
	public HashMap<String, DoubleData> getDoubles() {
		return doubles;
	}
	
	public HashMap<String, StringData> getStrings() {
		return strings;
	}
	
//	public HashMap<String, ItemData> getItems() {
//		return items;
//	}
//	
//	public HashMap<String, SkillData> getSkills() {
//		return skills;
//	}

	public int getInteger(String key) {
		return valueGetter(integers, key, 0);
	}

	public double getDouble(String key) {
		return valueGetter(doubles, key, 0.0);
	}

	public String getString(String key) {
		return valueGetter(strings, key, "");
	}

	private <V extends ObjectData<V, R>,R> R valueGetter(Map<String, V> map, String key, R defaultValue) {
		if (map == null) return defaultValue;
		if (!map.containsKey(key)) return defaultValue;
		return map.get(key).getRawValue();
	}

	public void setInteger(String key, int value) {
		(integers = data(integers, Maps::newHashMap)).put(key, new IntegerData(key, value));
	}

	public void setDouble(String key, double value) {
		(doubles = data(doubles, Maps::newHashMap)).put(key, new DoubleData(key, value));
	}

	public void setString(String key, String value) {
		(strings = data(strings, Maps::newHashMap)).put(key, new StringData(key, value));
	}

	private <R> HashMap<String, R> data(HashMap<String, R> origin, Supplier<HashMap<String, R>> constructor) {
		if (origin == null) origin = constructor.get();
		return origin;
	}
}
