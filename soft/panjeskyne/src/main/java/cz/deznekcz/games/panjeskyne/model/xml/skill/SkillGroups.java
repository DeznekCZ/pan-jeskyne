package cz.deznekcz.games.panjeskyne.model.xml.skill;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cz.deznekcz.games.panjeskyne.model.xml.ListType;

@XmlRootElement(name="skillgroups")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillGroups implements ListType<SkillGroup> {
	
	@XmlElement(name = "skillgroup")
	private List<SkillGroup> list;

	@Override
	public List<SkillGroup> getList() {
		return list;
	}

	public List<SkillGroup> getSkillGroups() {
		return getList();
	}
	
	public void setList(List<SkillGroup> list) {
		this.list = list;
	}
}
