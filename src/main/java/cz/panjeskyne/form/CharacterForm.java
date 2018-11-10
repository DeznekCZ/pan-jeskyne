package cz.panjeskyne.form;

import java.util.List;

public class CharacterForm implements Form {

	private Long id;
	
	private String name;
	
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

	public List<CharacterStatisticForm> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<CharacterStatisticForm> statistics) {
		this.statistics = statistics;
	}
	
}
