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
import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.model.xml.adapter.BonusMapAdapter;
import cz.deznekcz.games.panjeskyne.model.xml.adapter.SkillLevelMapAdapter;
import cz.deznekcz.games.panjeskyne.model.xml.skill.CharacterSkill;
import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillGroup;
import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillLevel;

@XmlRootElement(name = "skill")
@XmlAccessorType(XmlAccessType.FIELD)
public class Skill implements XmlMappable<String, Skill>, I18NTexts {
	
	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "desc")
	private String desc;

	@XmlAttribute(name = "hidden")
	private boolean hidden;

	@XmlAttribute(name = "limit")
	private int limit;
	
	@XmlTransient
	private SkillGroup skillgroup;

	@XmlJavaTypeAdapter(value = BonusMapAdapter.class)
	@XmlElement(name = "bonuses", nillable = false)
	private HashMap<String, Bonus> bonuses;

	@XmlJavaTypeAdapter(value = SkillLevelMapAdapter.class)
	@XmlElement(name = "levels", nillable = false)
	private HashMap<Integer, SkillLevel> levels;
	
	public String getDesc() {
		return desc == null ? "" : desc;
	}
	
	public String getName() {
		return name;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
	public int getLimit() {
		return limit == 0 ? 3 : limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getKey() {
		return id;
	}

	@Override
	public Skill getValue() {
		return this;
	}

	public void setSkillGroup(SkillGroup skillgroup) {
		this.skillgroup = skillgroup;
	}
	
	public SkillGroup getSkillgroup() {
		return skillgroup;
	}

	public void learnSkill(Character character, int level) {
		for (CharacterSkill skill : character.getSkills()) {
			if (skill.getRef().endsWith(getId())) {
				skill.setLevel(level);
				return;
			}
		}
		
		CharacterSkill cs = new CharacterSkill();
		cs.setRef(getId());
		cs.setLevel(level);
		character.getSkills().add(cs);
	}

	public HashMap<String, Bonus> getBonuses() {
		return bonuses == null ? bonuses = new HashMap<>() : bonuses;
	}
	
	public HashMap<Integer, SkillLevel> getLevels() {
		return levels == null ? levels = new HashMap<>() : levels;
	}
	
	public Bonus getSkillSimpleBonus(String statistic, int level) {
		return getBonuses().containsKey(statistic) ? getBonuses().get(statistic).leveled(level) : Bonus.NONE;
	}
	
	public Bonus getSkillLevelBonus(String statistic, int level) {
		if (getLevels().isEmpty()) {
			return Bonus.NONE;
		} else if (getLevels().containsKey(level)) {
			return getLevels().get(level).getSkillBonus(statistic);
		} else {
			return Bonus.NONE;
		}
	}
}
