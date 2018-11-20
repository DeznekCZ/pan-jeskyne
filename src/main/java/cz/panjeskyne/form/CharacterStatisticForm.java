package cz.panjeskyne.form;

public class CharacterStatisticForm implements Form {

	private Long id;
	
	private Long characterId;

	private Object statisticId;
	
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

	public Object getStatisticId() {
		return statisticId;
	}

	public void setStatisticId(Object statisticId) {
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
