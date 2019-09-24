package cz.deznekcz.games.panjeskyne.model.xml.skill;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cz.deznekcz.games.panjeskyne.model.xml.ListType;

@XmlRootElement(name="skills")
@XmlAccessorType(XmlAccessType.FIELD)
public class KindSkills implements ListType<KindSkill> {
	
	@XmlElement(name = "skill")
	private List<KindSkill> skill;

	@Override
	public List<KindSkill> getList() {
		return skill;
	}

	public List<KindSkill> getSkillGroups() {
		return getList();
	}
	
	public void setList(List<KindSkill> skill) {
		this.skill = skill;
	}
}
