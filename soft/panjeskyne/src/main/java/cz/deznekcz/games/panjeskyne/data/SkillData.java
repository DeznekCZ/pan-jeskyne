package cz.deznekcz.games.panjeskyne.data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "skill")
public class SkillData {

	@XmlAttribute(name = "id")
	private String id;

	public String getId() {
		return id;
	}

	public void init(String id) {
		this.id = id;
		//this.init();
	}
}
