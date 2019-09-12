package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import cz.deznekcz.games.panjeskyne.i18n.I18NTexts;
import cz.deznekcz.games.panjeskyne.model.enums.Gender;
import cz.deznekcz.games.panjeskyne.model.xml.adapter.BonusMapAdapter;
import cz.deznekcz.games.panjeskyne.model.xml.adapter.KindSkillMapAdapter;
import cz.deznekcz.games.panjeskyne.model.xml.skill.KindSkill;

@XmlRootElement(name = "kind")
@XmlAccessorType(XmlAccessType.FIELD)
public class Kind implements XmlMappable<String, Kind>, I18NTexts {

	public static final Kind EMPTY = new Kind();
	static {
		EMPTY.id = "#EMPTY";
		EMPTY.name = "---";
		EMPTY.race = Race.EMPTY;
	}

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "name")
	private String name;

	@XmlTransient
	private Race race;

	@XmlAttribute(name="gender")
	private Gender gender;

	@XmlJavaTypeAdapter(value = BonusMapAdapter.class)
	@XmlElement(name = "bonuses", nillable = false)
	private HashMap<String, Bonus> bonuses;

	@XmlJavaTypeAdapter(value = KindSkillMapAdapter.class)
	@XmlElement(name = "skills", nillable = false)
	private HashMap<String, KindSkill> skills;

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

	public HashMap<String, Bonus> getBonuses() {
		return bonuses == null ? bonuses = new HashMap<>() : bonuses;
	}
	
	public double getStatisticBonus(String codename) {
		return getBonuses().getOrDefault(codename, Bonus.NONE).getAddition();
	}

	@Override
	public String getKey() {
		return getCodename();
	}

	@Override
	public Kind getValue() {
		return this;
	}

	@Override
	public String getDesc() {
		return "";
	}

	public HashMap<String, KindSkill> getSkills() {
		return skills == null ? skills = new HashMap<>() : skills;
	}

}
