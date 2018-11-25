package cz.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="skillgroups")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillGroups implements ListType<SkillGroup> {
	
	@XmlElement(name = "skillgroup")
	private List<SkillGroup> kinds;

	@Override
	public List<SkillGroup> getList() {
		return kinds;
	}

	public List<SkillGroup> getSkillGroups() {
		return getList();
	}
	
}
