package cz.panjeskyne.model.xml.skill;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import cz.panjeskyne.model.xml.Bonus;
import cz.panjeskyne.model.xml.XmlMappable;
import cz.panjeskyne.model.xml.adapter.BonusMapAdapter;


@XmlRootElement(name = "level")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillLevel implements XmlMappable<Integer, SkillLevel> {

	@XmlAttribute(name = "level", required = true)
	private int level;

	@XmlJavaTypeAdapter(value = BonusMapAdapter.class)
	@XmlElement(name = "bonuses")
	private HashMap<String, Bonus> bonuses;
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public Integer getKey() {
		return getLevel();
	}

	@Override
	public SkillLevel getValue() {
		return this;
	}

	public HashMap<String, Bonus> getBonuses() {
		return bonuses == null ? bonuses = new HashMap<>() : bonuses;
	}
	
	public Bonus getSkillBonus(String statistic) {
		return getBonuses().getOrDefault(statistic, Bonus.NONE);
	}
	
}
