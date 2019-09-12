package cz.deznekcz.games.panjeskyne.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "skill")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterSkill {

	@XmlAttribute(name = "ref")
	private String skillCodename;

	@XmlAttribute(name = "level")
	private int skillLevel;

	public String getSkillCodename() {
		return skillCodename;
	}

	public void setSkillCodename(String skillCodename) {
		this.skillCodename = skillCodename;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}
}
