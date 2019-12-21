package cz.deznekcz.games.panjeskyne.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import cz.deznekcz.games.panjeskyne.drdplus2.data.SkillReference;
import cz.deznekcz.games.panjeskyne.model.xml.Description;
import cz.deznekcz.games.panjeskyne.model.xml.XmlSerialized;

@XmlRootElement(name="Character")
@XmlAccessorType(XmlAccessType.NONE)
public class Character implements XmlSerialized {

	private static final long serialVersionUID = 4619283962362366059L;

	@XmlAttribute(name = "id", required = true)
	private String id;

	@XmlAttribute(name = "name", required = true)
	private String name;
	
	@XmlElement(name = "description", required = true)
	private Description description;
	
	@XmlElementWrapper(name = "editors", required = true)
	@XmlElement(name ="editor", type=String.class, nillable = false)
	private List<String> editors;
	
	@XmlAttribute(name = "type")
	private CharacterType type;

	@XmlAttribute(name = "world", required = true)
	private String worldId;
	
	@XmlTransient
	private Exception error;
	
	@XmlAttribute(name = "background", required = true)
	private String background; // Id reference to world.background 

	@XmlElementWrapper(name = "skills")
	@XmlElement(name = "skill")
	private java.util.List<SkillReference> skills;

	public synchronized java.util.List<SkillReference> getSkills() {
		if (skills == null)
			skills = new java.util.ArrayList<>();
		return skills;
	}

	public synchronized void setSkills(java.util.Collection<SkillReference> skills) {
		this.skills = new java.util.ArrayList<>( skills );
	}

	public synchronized void addSkills(java.util.List<SkillReference> skills) {
		getSkills().addAll(skills);
	}

	public synchronized void removeSkills(java.util.List<SkillReference> skills) {
		getSkills().removeAll(skills);
	}
	
	@XmlElementWrapper(name = "stats")
	@XmlElement(name = "stat")
	private java.util.List<StatReference> stats;

	public synchronized java.util.List<StatReference> getStats() {
		if (stats == null)
			stats = new java.util.ArrayList<>();
		return stats;
	}

	public synchronized void setStats(java.util.Collection<StatReference> stats) {
		this.stats = new java.util.ArrayList<>( stats );;
	}

	public synchronized void addStats(java.util.List<StatReference> stats) {
		getStats().addAll(stats);
	}

	public synchronized void removeStats(java.util.List<StatReference> stats) {
		getStats().removeAll(stats);
	}
	
	public String getBackground() {
		return background;
	}
	
	public Character() {
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	
	public List<String> getEditors() {
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
	
	public CharacterType getType() {
		return type == null ? CharacterType.PC : type;
	}

	public String getWorldId() {
		return worldId;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description != null ? description.getValue() : "";
	}
	
	public void setDescription(String description) {
		if (this.description == null) {
			this.description = new Description();
		}
		this.description.setValue(description);
	}
}
