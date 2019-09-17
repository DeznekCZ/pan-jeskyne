package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlRootElement(name = "")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterDatas implements ListType<CharacterData> {

	private static final long serialVersionUID = -6783314417727975806L;
	
	@XmlElement(name = "data", required = false)
	private List<CharacterData> list;

	@Override
	public List<CharacterData> getList() {
		return list;
	}

	public double getValue(String key) {
		if (list == null) return 0;
		for (CharacterData characterData : list) {
			if (characterData.getKey().equals(key)) {
				return characterData.getValue();
			}
		}
		return 0;
	}

	public void setValue(String key, double value) {
		if (list == null) {
			list = Lists.newArrayList();
		} else {
			for (CharacterData characterData : list) {
				if (characterData.getKey().equals(key)) {
					characterData.setValue(value);
					return;
				}
			}
		}
		// in case of non existent
		CharacterData data = new CharacterData();
		data.setKey(key);
		data.setValue(value);
		list.add(data);
	}

}
