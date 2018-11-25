package cz.panjeskyne.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import cz.panjeskyne.i18n.I18NTexts;
import cz.panjeskyne.service.I18NService;

@XmlRootElement(name = "skill")
@XmlAccessorType(XmlAccessType.FIELD)
public class Skill implements XmlMappable<String, Skill>, I18NTexts {
	
	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "desc")
	private String desc;

	@XmlAttribute(name = "hidden")
	private boolean hidden;

	@XmlAttribute(name = "limit")
	private int limit;
	
	@XmlTransient
	private SkillGroup skillgroup;
	
	public String getDesc() {
		return desc;
	}
	
	public String getName() {
		return name;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getKey() {
		return id;
	}

	@Override
	public Skill getValue() {
		return this;
	}

	public void setSkillGroup(SkillGroup skillgroup) {
		this.skillgroup = skillgroup;
	}
	
	public SkillGroup getSkillgroup() {
		return skillgroup;
	}
}
