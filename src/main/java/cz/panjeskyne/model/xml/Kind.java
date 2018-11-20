package cz.panjeskyne.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import cz.panjeskyne.model.enums.Gender;

@XmlRootElement(name = "kind")
@XmlAccessorType(XmlAccessType.FIELD)
public class Kind implements XmlMappable<String, Kind> {

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "name")
	private String name;

	@XmlTransient
	private Race race;

	@XmlAttribute(name="gender")
	private Gender gender;

//	@XmlElement(name="bonus")
//	private List<KindStatisticBonus> bonuses;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCodename() {
		return id;
	}

	public int getStatistic(String codename) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getKey() {
		return getCodename();
	}

	@Override
	public Kind getValue() {
		return this;
	}

}
