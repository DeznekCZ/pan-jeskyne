package cz.panjeskyne.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="statistic")
public class Statistic extends AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Override
	public Object getId() {
		return id;
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
	
}
