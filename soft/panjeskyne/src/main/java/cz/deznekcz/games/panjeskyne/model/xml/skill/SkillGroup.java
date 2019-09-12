package cz.deznekcz.games.panjeskyne.model.xml.skill;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import cz.deznekcz.games.panjeskyne.i18n.I18NTexts;
import cz.deznekcz.games.panjeskyne.model.xml.Skill;
import cz.deznekcz.games.panjeskyne.model.xml.adapter.SkillMapAdapter;

@XmlRootElement(name = "skillgroup")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillGroup implements I18NTexts {
	
	@XmlAttribute(name = "id")
	private String id;
	
	@XmlAttribute(name = "name")
	private String name;
	
	@XmlAttribute(name = "desc")
	private String desc;
	
	@XmlJavaTypeAdapter(value = SkillMapAdapter.class)
	@XmlElement(name = "skills")
	private HashMap<String, Skill> skills;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCodename() {
		return getId();
	}

	public HashMap<String, Skill> getSkills() {
		return skills == null ? skills = new HashMap<>() : skills;
	}
}
