package cz.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Kinds implements ListType<Kind> {
	
	@XmlElement(name = "kind")
	private List<Kind> kinds;

	@Override
	public List<Kind> getList() {
		return kinds;
	}
	
}
