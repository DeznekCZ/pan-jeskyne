package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cz.deznekcz.games.panjeskyne.model.xml.adapter.DescriptionHandler;
import cz.deznekcz.games.panjeskyne.model.xml.skill.CharacterSkill;
import cz.deznekcz.games.panjeskyne.model.xml.skill.CharacterSkills;

@XmlRootElement(name="character")
@XmlAccessorType(XmlAccessType.NONE)
public class Character implements XmlSerialized {

	private static final long serialVersionUID = 4619283962362366059L;

	private static final List<CharacterSkill> EMPTY = new ArrayList<>(0);

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "owner")
	private String owner;
	
	@XmlAttribute(name = "kind")
	private String kindCodename;
	
	@XmlAttribute(name = "experience", required = false)
	private int experience;
	
	@XmlAttribute(name = "level", required = false)
	private int level;
	
//	@XmlAnyElement(value=DescriptionHandler.class)
//	private String description;
	@XmlElement(name = "desc")
	private Description descriprion;

	@XmlElement(name = "skills", required = false)
	private CharacterSkills skills;
	
	@XmlElement(name = "data", required = false)
	private CharacterDatas data; 

	@XmlAttribute(name = "module")
	private String module;

	private String errorText;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKindCodename() {
		return kindCodename;
	}

	public void setKindCodename(String kindCodename) {
		this.kindCodename = kindCodename;
	}

	public List<CharacterSkill> getSkills() {
		return skills != null && skills.getList() != null ? skills.getList() : EMPTY;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setKind(Kind kind) {
		setKindCodename(kind != null ? kind.getCodename() : null);
	}

	public void setDescription(String description) {
//		this.description = description;
		if (descriprion == null) this.descriprion = new Description();
		this.descriprion.setText(description);
	}

	public String getDescription() {
//		return description;
		return descriprion != null ? descriprion.getText() : "";
	}

	public void setError(String errorText) {
		this.errorText = errorText;
	}
	
	public String getErrorText() {
		return errorText;
	}

	public String getModule() {
		return module;
	}
	
	public int getExperience() {
		return experience;
	}
	
	public int getLevel() {
		return level;
	}

	public double getData(String key) {
		switch (key) {
		case "character.experience": return getExperience();
		case "character.level": return getLevel();
		default: return data != null ? data.getValue(key) : 0;
		}
	}

	public void setData(String key, double value) {
		switch (key) {
		case "character.experience": setExperience((int) value); break;
		case "character.level": setLevel((int) value); break;
		default: {
			if (data == null) data = new CharacterDatas();
			data.setValue(key, value);
		}
		}
	}

	public String getId() {
		return id;
	}

	public void learn(String id, int level) {
		if (skills == null) {
			skills = new CharacterSkills();
		}
		skills.setLevel(id, level);
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setExperience(int experience) {
		this.experience = experience;
	}
}
