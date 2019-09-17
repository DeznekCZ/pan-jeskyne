package cz.deznekcz.games.panjeskyne.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterData implements XmlSerialized {
	
	private static final long serialVersionUID = 5209865375432610607L;

	@XmlAttribute(name = "key", required = true)
	private String key;
	
	@XmlAttribute(name = "value", required = true)
	private double value;
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
}
