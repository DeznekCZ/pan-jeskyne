package cz.panjeskyne.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="statistic")
@XmlAccessorType(XmlAccessType.NONE)
public class Statistic {

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "name")
	private String name;
	
	@XmlAttribute(name = "groups", required = false)
	private String groups;

	@XmlValue
	private String formula;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodename() {
		return id;
	}

	public void setCodename(String codename) {
		this.id = codename;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroups() {
		return groups;
	}
	
	public void setGroups(String groups) {
		this.groups = groups;
	}
	
	public boolean hasGroups() {
		return getGroups() != null && getGroups().length() > 0;
	}
	
	public boolean hasFormula() {
		return getFormula() != null && getFormula().length() > 0;
	}

	public boolean isVoid() {
		return "void".equals(id);
	}

}
