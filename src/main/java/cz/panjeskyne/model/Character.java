package cz.panjeskyne.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "character")
public class Character extends AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "character", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<CharacterStatistic> statistics;

	public List<CharacterStatistic> getStatistics() {
		if (statistics == null) {
			statistics = new ArrayList<>();
		}
		return statistics;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatistics(List<CharacterStatistic> statistics) {
		this.statistics = statistics;
	}

	@Transient
	public CharacterStatistic getStatistikaPostavy(Long id) {
		if (id == null || id == 0) {
			return null;
		}
		return getStatistics().stream().filter(item -> item.getId() != null && item.getId().equals(id)).findFirst()
				.orElse(null);
	}

	@Override
	public Object getId() {
		return id;
	}

}
