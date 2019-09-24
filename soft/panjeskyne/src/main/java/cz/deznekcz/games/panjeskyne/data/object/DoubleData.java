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
public class DoubleData implements ObjectData<DoubleData, Double> {

	private static final long serialVersionUID = -3492187173719382189L;

	@XmlRootElement
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class DoubleDataList implements ListType<DoubleData> {

		private static final long serialVersionUID = 3339336087456765627L;
		
		@XmlElement(name = "entry")
		private List<DoubleData> list;

		@Override
		public List<DoubleData> getList() {
			return list;
		}

		@Override
		public void setList(List<DoubleData> list) {
			this.list = list;
		}
	}
	
	public static class Adapter extends ListToMapAdapter<String, DoubleData, DoubleDataList, DoubleData> {

		@Override
		public DoubleDataList marshal(HashMap<String, DoubleData> v) throws Exception {
			return build(DoubleDataList::new, v);
		}
	}

	@XmlAttribute(name = "key")
	private String key;

	@XmlAttribute(name = "value")
	private Double value;
	
	public DoubleData() {
		// FOR XML LOAD
	}

	public DoubleData(String key, Double value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public DoubleData getValue() {
		return this;
	}

	@Override
	public Double getRawValue() {
		return value;
	}
}
