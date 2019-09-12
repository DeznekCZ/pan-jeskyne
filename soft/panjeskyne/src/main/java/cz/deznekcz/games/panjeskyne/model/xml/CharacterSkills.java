package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "skills")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterSkills implements ListType<CharacterSkill> {

	@XmlElement(name = "skill", required = false)
	private List<CharacterSkill> list;

	@Override
	public List<CharacterSkill> getList() {
		return list;
	}

}
