package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Skills implements ListType<Skill> {
	
	private static final long serialVersionUID = 8321009715480865325L;
	
	@XmlElement(name = "skill")
	private List<Skill> list;

	@Override
	public List<Skill> getList() {
		return list;
	}
	
	@Override
	public void setList(List<Skill> list) {
		this.list = list;
	}
}
