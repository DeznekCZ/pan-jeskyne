package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bonuses")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bonuses implements ListType<Bonus>, XmlSerialized {

	@XmlElement(name = "bonus")
	private List<Bonus> bonuses;
	
	@Override
	public List<Bonus> getList() {
		return bonuses;
	}

	public List<Bonus> getBonuses() {
		return getList();
	}
}
