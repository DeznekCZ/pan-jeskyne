package cz.panjeskyne.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "bonus")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bonus implements XmlMappable<String, Bonus> {

	public static final Bonus NONE = new Bonus();

	@XmlAttribute(name = "ref", required = true)
	private String ref;

	/** Raw value */
	@XmlAttribute(name = "addition")
	private double addition = 0;
	
	/** Percentage */
	@XmlAttribute(name = "multiply")
	private double multiply = 0; 
	
	@XmlTransient
	private Skill skill;
	
	@Override
	public String getKey() {
		return ref;
	}
	
	public String getRef() {
		return ref;
	}

	@Override
	public Bonus getValue() {
		return this;
	}
	
	public Skill getSkill() {
		return skill;
	}
	
	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
	public void setMultiply(double multiply) {
		this.multiply = multiply;
	}
	
	public double getMultiply() {
		return multiply;
	}
	
	public void setAddition(double addition) {
		this.addition = addition;
	}
	
	public double getAddition() {
		return addition;
	}

	public Bonus leveled(int level) {
		Bonus b = new Bonus();
		b.addition = level * addition;
		b.multiply = level * multiply;
		return b;
	}
}
