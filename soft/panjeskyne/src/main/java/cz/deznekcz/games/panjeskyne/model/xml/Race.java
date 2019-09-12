package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import cz.deznekcz.games.panjeskyne.model.xml.adapter.KindMapAdapter;

@XmlRootElement(name = "race")
@XmlAccessorType(XmlAccessType.FIELD)
public class Race {

	public static final Race EMPTY = new Race();
	static {
		EMPTY.id = "#EMPTY";
		EMPTY.name = "---";
	}

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

	public Map<String, Kind> getKinds() {
		return kinds;
	}
}
