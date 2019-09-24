package cz.deznekcz.games.panjeskyne.data;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;

import cz.deznekcz.games.panjeskyne.model.xml.XmlMappable;
import cz.deznekcz.games.panjeskyne.model.xml.adapter.ListToMapAdapter;

public class SkillData implements XmlMappable<String, SkillData> {

	private static final long serialVersionUID = -2905796975731951838L;

	public static class SkillDataList extends SimpleListType<SkillData> {
		private static final long serialVersionUID = -93435451644940614L;
	}

	public class Adapter extends ListToMapAdapter<String, SkillData, SkillDataList, SkillData> {

		@Override
		public SkillDataList marshal(HashMap<String, SkillData> v) throws Exception {
			return build(SkillDataList::new, v);
		}

	}

	@XmlElement(name = "id")
	private String id;

	@Override
	public String getKey() {
		return id;
	}

	@Override
	public SkillData getValue() {
		return this;
	}

	public void init(String id) {
		this.id = id;
		//this.init();
	}
}
