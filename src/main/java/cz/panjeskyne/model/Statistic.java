package cz.panjeskyne.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "statistic", uniqueConstraints = { @UniqueConstraint(columnNames = { "codename" }) })
public class Statistic extends AbstractEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "codename", length = 10, nullable = false)
	private String codename;
	
	@Column(name = "formula", nullable = false)
	private String formula;

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

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
