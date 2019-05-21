package cz.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "skills")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceSkills implements ListType<RaceSkill> {
	
	@XmlElement(name = "skill")
	private List<RaceSkill> kinds;

	@Override
	public List<RaceSkill> getList() {
		return kinds;
	}
}
