package cz.panjeskyne.form;

import java.util.List;

public class CharacterForm implements Form {

	private Long id;
	
	private String name;
	
	private String race;

	private String kind;
	
	private List<CharacterStatisticForm> statistics;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public List<CharacterStatisticForm> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<CharacterStatisticForm> statistics) {
		this.statistics = statistics;
	}
	
}
