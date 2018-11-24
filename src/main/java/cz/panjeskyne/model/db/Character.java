package cz.panjeskyne.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "character")
public class Character extends AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "kind_codename")
	private String kindCodename;

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

	@Override
	public Object getId() {
		return id;
	}

}
