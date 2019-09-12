package cz.deznekcz.games.panjeskyne.model.xml.skill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import cz.deznekcz.games.panjeskyne.model.xml.XmlMappable;

@XmlRootElement(name = "skill")
@XmlAccessorType(XmlAccessType.FIELD)
public class KindSkill implements XmlMappable<String, KindSkill> {
	
	@XmlAttribute(name = "ref", required = true)
	private String ref;
	
	@XmlAttribute(name = "level", required = true)
	private int level;

	@Override
	public String getKey() {
		return getRef();
	}

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
	public KindSkill getValue() {
		return this;
	}

	public String getSkillCodename() {
		return getRef();
	}

	public int getSkillLevel() {
		return getLevel();
	}
}
