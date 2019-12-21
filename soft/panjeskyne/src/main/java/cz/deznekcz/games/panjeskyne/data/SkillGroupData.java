package cz.deznekcz.games.panjeskyne.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import cz.deznekcz.games.i18n.I18NText;
import cz.deznekcz.games.panjeskyne.i18n.I18NContainer;

@XmlRootElement(name = "skillgroup")
@XmlAccessorType(XmlAccessType.NONE)
public class SkillGroupData implements I18NContainer {

	@XmlAttribute(name = "id")
	private String id;

	@XmlAttribute(name = "group")
	private String group;
	
	@XmlElementWrapper(name = "descriptions")
	@XmlElement(name = "description")
	private List<I18NText> descriptions;
	
	@Override
	public List<I18NText> getDescriptions() {
		return descriptions;
	}
	
	@XmlElementWrapper(name = "names")
	@XmlElement(name = "name")
	private List<I18NText> names;
	
	@Override
	public List<I18NText> getNames() {
		return names;
	}

	public String getId() {
		return id;
	}
	
	public String getGroup() {
		return group;
	}

	public void init(String id) {
		this.id = id;
		//this.init();
	}

	public boolean hasGroup() {
		return group != null && !group.isEmpty();
	}
	
}