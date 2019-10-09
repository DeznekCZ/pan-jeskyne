package cz.deznekcz.games.panjeskyne.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import cz.deznekcz.games.panjeskyne.model.xml.Skill;
import cz.deznekcz.games.panjeskyne.model.xml.XmlSerialized;
import cz.deznekcz.games.panjeskyne.service.CharacterService;
import cz.deznekcz.games.panjeskyne.service.SkillService;

@XmlRootElement(name = "World")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorldData implements XmlSerialized {
	
	private static final long serialVersionUID = -2782239646790420463L;

	public static final WorldData NONE = new WorldData();
	static {
		NONE.init("none");
	}

	@XmlAttribute(name = "id", required = true)
	private String id;
	
	@XmlAttribute(name = "name", required = true)
	private String name;
	
	@XmlElement(name = "description", required = true)
	private String description;
	
	@XmlElement(name = "editors")
	private List<String> editors;
	
	@XmlElement(name = "characters")
	private List<String> characters;
	
	@XmlElement(name = "skills")
	private List<String> skills;
	
	@XmlElement(name = "races")
	private List<String> races;
	
	@XmlTransient
	private Exception error;
	
	public WorldData() {
		this.name = "---";
		this.description = "";
	}
	
	public String getId() {
		return id;
	}
	
	public synchronized List<String> getEditors() {
		if (editors == null) {
			editors = new ArrayList<>();
		}
		return editors;
	}
	
	public void setError(Exception error) {
		this.error = error;
	}
	
	public Exception getError() {
		return error;
	}

	public void init(String id) {
		this.id = id;
	}
	
	public synchronized List<String> getCharacters() {
		if (characters == null) {
			characters = new ArrayList<>();
		}
		return characters;
	}
	
	public synchronized List<String> getSkills() {
		if (skills == null) {
			skills = new ArrayList<>();
		}
		return skills;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}
}
