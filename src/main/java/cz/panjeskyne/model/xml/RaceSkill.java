package cz.panjeskyne.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "skill")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceSkill implements XmlMappable<String, RaceSkill> {

	@XmlAttribute(name = "ref", required = true)
	private String ref;

	@XmlAttribute(name = "level", required = true)
	private int level;
	
	public String getRef() {
		return ref;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	@Override
	public String getKey() {
		return getRef();
	}

	@Override
	public RaceSkill getValue() {
		return this;
	}

}
