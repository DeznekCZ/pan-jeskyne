package rejected;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.common.collect.Maps;

import cz.deznekcz.games.panjeskyne.data.CharacterRulesData;
import cz.deznekcz.games.panjeskyne.data.CharacterType;
import cz.deznekcz.games.panjeskyne.data.Descripted;
import cz.deznekcz.games.panjeskyne.data.LangData;
import cz.deznekcz.games.panjeskyne.data.Named;
import cz.deznekcz.games.panjeskyne.data.StringList;
import cz.deznekcz.games.panjeskyne.i18n.I18N;
import cz.deznekcz.games.panjeskyne.model.xml.XmlSerialized;
import cz.deznekcz.games.panjeskyne.model.xml.adapter.SimpleMapAdapter;

@XmlRootElement(name="Character")
@XmlAccessorType(XmlAccessType.NONE)
public class Character implements XmlSerialized, Named, Descripted {

	private static final long serialVersionUID = 4619283962362366059L;

	@XmlAttribute(name = "id", required = true)
	private String id;

//	@XmlJavaTypeAdapter(value = LangDataMapAdapter.class)
	@XmlElement(name = "name", required = true)
	private HashMap<String, LangData> name;
	
//	@XmlJavaTypeAdapter(value = LangDataMapAdapter.class)
	@XmlElement(name = "description", required = true)
	private HashMap<String, LangData> description;
	
	@XmlElement(name = "editors")
	private StringList editors;
	
	@XmlAttribute(name = "type")
	private CharacterType type;

	@XmlAttribute(name = "world", required = true)
	private String worldId;
	
	@XmlTransient
	private Exception error;
	
	
	@XmlJavaTypeAdapter(value = CharacterRulesData.Adapter.class)
	@XmlElement(name = "data", required = true)
	private HashMap<String, CharacterRulesData> data;
	
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
	
	public CharacterType getType() {
		return type == null ? CharacterType.PC : type;
	}

	public CharacterRulesData getData(String rules) {
		if (!data.containsKey(rules)) {
			CharacterRulesData newData = new CharacterRulesData();
			newData.init(rules);
			data.put(rules, newData);
		}
		return data.get(rules);
	}

	@Override
	public Map<String, LangData> getDescriptionMap() {
		return description;
	}

	@Override
	public Map<String, LangData> getNameMap() {
		return name;
	}

	public String getWorldId() {
		return worldId;
	}
}
