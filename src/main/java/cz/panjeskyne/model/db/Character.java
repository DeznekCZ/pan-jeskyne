package cz.panjeskyne.model.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "character")
public class Character extends AbstractEntity {

	private static final List<CharacterSkill> EMPTY = new ArrayList<>(0);

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "kind_codename")
	private String kindCodename;

	@OneToMany(mappedBy = "character")
	private List<CharacterSkill> skills;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKindCodename() {
		return kindCodename;
	}

	public void setKindCodename(String kindCodename) {
		this.kindCodename = kindCodename;
	}

	public List<CharacterSkill> getSkills() {
		return skills == null ? EMPTY : skills;
	}

	public void setSkills(List<CharacterSkill> skills) {
		this.skills = skills;
	}

	@Override
	public Object getId() {
		return id;
	}

}
