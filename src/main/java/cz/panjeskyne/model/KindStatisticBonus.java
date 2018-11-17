package cz.panjeskyne.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "kind_statistic_bonus")
public class KindStatisticBonus extends AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "statistic_id", nullable = false)
	private Statistic statistic;

	@ManyToOne
	@JoinColumn(name = "kind_id", nullable = false)
	private Kind kind;

	@Column(name = "bonusValue")
	private int bonusValue;

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

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public int getBonusValue() {
		return bonusValue;
	}

	public void setBonusValue(int value) {
		this.bonusValue = value;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
