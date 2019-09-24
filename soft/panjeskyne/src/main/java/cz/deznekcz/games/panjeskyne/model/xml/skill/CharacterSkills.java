package cz.deznekcz.games.panjeskyne.model.xml.skill;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cz.deznekcz.games.panjeskyne.model.xml.ListType;

@XmlRootElement(name = "skills")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterSkills implements ListType<CharacterSkill> {

	private static final long serialVersionUID = 801396725806657898L;
	
	@XmlElement(name = "skill", required = false)
	private List<CharacterSkill> list;

	@Override
	public List<CharacterSkill> getList() {
		return list;
	}

	public void init() {
		list = new ArrayList<>();
	}

	public void setLevel(String id, int level) {
		if (list == null) list = new ArrayList<>();
		
		for (CharacterSkill characterSkill : list) {
			if (characterSkill.getRef().equals(id)) {
				if (level == 0) {
					list.remove(characterSkill);
				} else {
					characterSkill.setLevel(level);
				}
				return;
			}
		}
		
		if (level > 0) {
			CharacterSkill skill = new CharacterSkill();
			skill.setRef(id);
			skill.setLevel(level);
			list.add(skill);
		}
	}

	@Override
	public void setList(List<CharacterSkill> list) {
		this.list = list;
	}

}
