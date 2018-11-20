package cz.panjeskyne.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "bonus")
@XmlAccessorType(XmlAccessType.FIELD)
public class KindStatisticBonus {

	@XmlAttribute(name= "ref")
	private String statisticCodename;

	@XmlTransient
	private Statistic statistic;

	@XmlTransient
	private Kind kind;

	@XmlAttribute(name= "transient")
	private int bonusValue;


	public Statistic getStatistic() {
		return statistic;
	}

	public void setStatistic(Statistic statistic) {
		this.statistic = statistic;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public int getBonusValue() {
		return bonusValue;
	}

	public void setBonusValue(int value) {
		this.bonusValue = value;
	}

}
