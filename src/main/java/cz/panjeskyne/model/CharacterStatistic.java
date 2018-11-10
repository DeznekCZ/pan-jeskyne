package cz.panjeskyne.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "character_statistic")
public class CharacterStatistic extends AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "statistic_id")
	private Statistic statistic;

	@ManyToOne
	@JoinColumn(name = "character_id")
	private Character character;

	@Column(name = "value")
	private int value;

	@Override
	public Object getId() {
		return id;
	}

	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
