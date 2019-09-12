package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class Statistics {
	
	@XmlElement(name = "statistic")
	private List<Statistic> statistics;

	public Statistics() {
		statistics = new ArrayList<>();
	}
	
	public List<Statistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Statistic> statistics) {
		this.statistics = statistics;
	}
}
