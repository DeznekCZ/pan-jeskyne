package cz.panjeskyne.model.xml;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import cz.panjeskyne.model.xml.adapter.KindMapAdapter;

@XmlRootElement(name = "race")
@XmlAccessorType(XmlAccessType.FIELD)
public class Race {

	@XmlAttribute(name = "id")
	private String id;
	
	@XmlAttribute(name = "name")
	private String name;

	@XmlJavaTypeAdapter(value = KindMapAdapter.class)
	@XmlElement(name = "kinds")
	private HashMap<String, Kind> kinds;

	public Object getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getCodename() {
		return id;
	}

	public HashMap<String, Kind> getKinds() {
		return kinds;
	}
}
