package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="bonuses")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bonuses implements ListType<Bonus>, XmlSerialized {

	private static final long serialVersionUID = -2103148548108196380L;
	
	@XmlElement(name = "bonus")
	private List<Bonus> bonuses;
	
	@Override
	public List<Bonus> getList() {
		return bonuses;
	}
	
	public void setList(List<Bonus> bonuses) {
		this.bonuses = bonuses;
	}

	public List<Bonus> getBonuses() {
		return getList();
	}
}
