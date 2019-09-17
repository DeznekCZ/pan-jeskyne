package cz.deznekcz.games.panjeskyne.service.helper;

import cz.deznekcz.games.panjeskyne.model.xml.Skill;
import cz.deznekcz.games.panjeskyne.model.xml.XmlSerialized;
import cz.deznekcz.games.panjeskyne.module.AModule;

public class SkillData implements Comparable<SkillData>, XmlSerialized {

	private static final long serialVersionUID = 1L;
	
	private String ref;
	private int level;
	private Skill skill;
	private boolean removable;
	
	public SkillData(String ref, int level, AModule module) {
		this.ref = ref;
		this.level = level;
		this.skill = module.getSkillService().getByCodename(ref);
	}
	
	@Override
	public int compareTo(SkillData o) {
		if (skill == null && o.skill == null) return 0;
		if (skill == null && o.skill != null) return -1;
		if (skill != null && o.skill == null) return 1;
		
		return skill.getName().compareTo(o.skill.getName());
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof SkillData && this.equalsTo((SkillData) obj);
	}

	private boolean equalsTo(SkillData obj) {
		return this.getRef().equals(obj.getRef());
	}

	public String getRef() {
		return ref;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Skill getSkill() {
		return skill;
	}
	
	@Override
	public String toString() {
		return ref + ":" + level;
	}

	public boolean isFound() {
		return skill != null;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public boolean isRemovable() {
		return removable;
	}
}
