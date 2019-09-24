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
public class StringData implements ObjectData<StringData, String> {

	private static final long serialVersionUID = -3492187173719382189L;

	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class StringDataList implements ListType<StringData> {

		private static final long serialVersionUID = 3339336087456765627L;
		
		@XmlElement(name = "entry")
		private List<StringData> list;

		@Override
		public List<StringData> getList() {
			return list;
		}

		@Override
		public void setList(List<StringData> list) {
			this.list = list;
		}
	}
	
	public static class Adapter extends ListToMapAdapter<String, StringData, StringDataList, StringData> {

		@Override
		public StringDataList marshal(HashMap<String, StringData> v) throws Exception {
			return build(StringDataList::new, v);
		}
	}

	@XmlAttribute(name = "key")
	private String key;

	@XmlAttribute(name = "value")
	private String value;
	
	public StringData() {
		// FOR XML LOAD
	}

	public StringData(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public StringData getValue() {
		return this;
	}

	@Override
	public String getRawValue() {
		return value;
	}
}
