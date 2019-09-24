package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Kinds implements ListType<Kind> {
	
	private static final long serialVersionUID = -6295234565969132452L;
	
	@XmlElement(name = "kind")
	private List<Kind> kinds;

	@Override
	public List<Kind> getList() {
		return kinds;
	}
	
	public void setList(List<Kind> kinds) {
		this.kinds = kinds;
	}
}
