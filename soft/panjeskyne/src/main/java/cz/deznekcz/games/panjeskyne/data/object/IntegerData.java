package cz.deznekcz.games.panjeskyne.data.object;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cz.deznekcz.games.panjeskyne.model.xml.ListType;
import cz.deznekcz.games.panjeskyne.model.xml.adapter.ListToMapAdapter;

@XmlRootElement(name = "entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class IntegerData implements ObjectData<IntegerData, Integer> {

	private static final long serialVersionUID = -3492187173719382189L;

	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class IntegerDataList implements ListType<IntegerData> {

		private static final long serialVersionUID = 3339336087456765627L;
		
		@XmlElement(name = "entry")
		private List<IntegerData> list;

		@Override
		public List<IntegerData> getList() {
			return list;
		}

		@Override
		public void setList(List<IntegerData> list) {
			this.list = list;
		}
	}
	
	public static class Adapter extends ListToMapAdapter<String, IntegerData, IntegerDataList, IntegerData> {

		@Override
		public IntegerDataList marshal(HashMap<String, IntegerData> v) throws Exception {
			return build(IntegerDataList::new, v);
		}
	}

	@XmlAttribute(name = "key")
	private String key;

	@XmlAttribute(name = "value")
	private Integer value;
	
	public IntegerData() {
		// FOR XML LOAD
	}

	public IntegerData(String key, Integer value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public IntegerData getValue() {
		return this;
	}

	@Override
	public Integer getRawValue() {
		return value;
	}
}
