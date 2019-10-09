package cz.deznekcz.games.panjeskyne.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.NONE)
public class StatReference {

	private static final long serialVersionUID = 1L;

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "value")
	private Integer value;
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
