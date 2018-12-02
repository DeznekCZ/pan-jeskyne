package cz.panjeskyne.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "character_skill")
public class CharacterSkill extends AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "character_id")
	private Character character;

	@Column(name = "skill_codename")
	private String skillCodename;

	@Column(name = "skill_level")
	private int skillLevel;

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public String getSkillCodename() {
		return skillCodename;
	}

	public void setSkillCodename(String skillCodename) {
		this.skillCodename = skillCodename;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}

	@Override
	public Object getId() {
		return id;
	}
}
