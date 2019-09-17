package cz.deznekcz.games.panjeskyne.model.xml.skill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import cz.deznekcz.games.panjeskyne.model.xml.XmlSerialized;

@XmlRootElement(name = "skill")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterSkill implements XmlSerialized {

	private static final long serialVersionUID = 6027496389698146229L;

	@XmlAttribute(name = "ref")
	private String ref;

	@XmlAttribute(name = "level")
	private int level;

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return ref + ":" + level;
	}
}
