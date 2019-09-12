package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Skills implements ListType<Skill> {
	
	@XmlElement(name = "skill")
	private List<Skill> kinds;

	@Override
	public List<Skill> getList() {
		return kinds;
	}
	
}
