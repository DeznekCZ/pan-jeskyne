package cz.deznekcz.games.panjeskyne.drdplus2.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import cz.deznekcz.games.panjeskyne.data.ICharSkill;

@XmlRootElement(name = "skill")
@XmlAccessorType(XmlAccessType.NONE)
public class SkillReference implements ICharSkill {
	
	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "id", required = true)
	private String id;
	
	@XmlAttribute(name = "level")
	private Integer level;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getLevel() {
		return level;
	}

}
