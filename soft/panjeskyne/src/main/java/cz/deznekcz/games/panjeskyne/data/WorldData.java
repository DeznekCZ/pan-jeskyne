package cz.deznekcz.games.panjeskyne.data;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.collect.Maps;

import cz.deznekcz.games.panjeskyne.i18n.I18N;
import cz.deznekcz.games.panjeskyne.model.xml.XmlSerialized;
import cz.deznekcz.games.panjeskyne.service.CharacterService;

@XmlRootElement(name = "World")
@XmlAccessorType(XmlAccessType.FIELD)
public class WorldData implements XmlSerialized, Named, Descripted {
	
	private static final long serialVersionUID = -2782239646790420463L;

	@XmlAttribute(name = "id", required = true)
	private String id;
	
	@XmlJavaTypeAdapter(value = LangDataMapAdapter.class)
	@XmlElement(name = "name", required = true)
	private HashMap<String, LangData> name;
	
	@XmlJavaTypeAdapter(value = LangDataMapAdapter.class)
	@XmlElement(name = "description", required = true)
	private HashMap<String, LangData> description;
	
	@XmlElement(name = "editors")
	private StringList editors;
	
	@XmlTransient
	private Exception error;
	
	public String getId() {
		return id;
	}
	
	public StringList getEditors() {
		if (editors == null) {
			editors = new StringList();
		}
		return editors;
	}
	
	public void setError(Exception error) {
		this.error = error;
	}
	
	public Exception getError() {
		return error;
	}

	public void init(String id) {
		this.id = id;
		this.name = Maps.newHashMap();
		this.name.put(I18N.LOCALE, new LangData());
		this.description = Maps.newHashMap();
		this.description.put(I18N.LOCALE, new LangData());
	}
	
	public List<Character> getCharacters() {
		return CharacterService.getWorldCharacters(this.id);
	}

	@Override
	public Map<String, LangData> getDescriptionMap() {
		return description;
	}

	@Override
	public Map<String, LangData> getNameMap() {
		return name;
	}
}
