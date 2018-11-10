package cz.panjeskyne.form;

public class CharacterStatisticForm implements Form {

	private Long id;
	
	private Long characterId;

	private long statisticId;
	
	private String name;
	
	private int value;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCharacterId() {
		return characterId;
	}

	public void setCharacterId(Long characterId) {
		this.characterId = characterId;
	}

	public long getStatisticId() {
		return statisticId;
	}

	public void setStatisticId(long statisticId) {
		this.statisticId = statisticId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
