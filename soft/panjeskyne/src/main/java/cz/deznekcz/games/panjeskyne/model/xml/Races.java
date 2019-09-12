package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="races")
@XmlAccessorType(XmlAccessType.FIELD)
public class Races {
	
	@XmlElement(name = "race")
	private List<Race> races;

	public Races() {
		races = new ArrayList<>();
	}

	public List<Race> getRaces() {
		return races;
	}

	public void setRaces(List<Race> races) {
		this.races = races;
	}
	
}
