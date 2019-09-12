package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

@XmlRootElement(name="character")
@XmlAccessorType(XmlAccessType.NONE)
public class Character {

	private static final List<CharacterSkill> EMPTY = new ArrayList<>(0);

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "owner")
	private String owner;
	
	@XmlAttribute(name = "kind")
	private String kindCodename;
	
	@XmlAttribute(name = "desc", required = false)
	private String description;

	@XmlElement(name = "skills", required = false)
	private CharacterSkills skills;

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
		return skills.getList() != null ? skills.getList() : EMPTY;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setKind(Kind kind) {
		setKindCodename(kind != null ? kind.getCodename() : null);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setError(String errorText) {
		this.errorText = errorText;
	}
}
