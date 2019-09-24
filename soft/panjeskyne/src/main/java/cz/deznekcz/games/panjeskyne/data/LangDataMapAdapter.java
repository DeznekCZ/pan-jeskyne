package cz.deznekcz.games.panjeskyne.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import cz.deznekcz.games.panjeskyne.model.xml.adapter.ListToMapAdapter;

public class LangDataMapAdapter extends ListToMapAdapter<String, LangData, LangDataList, LangData> {

	@Override
	public LangDataList marshal(HashMap<String, LangData> v) throws Exception {
		LangDataList sil = new LangDataList();
		List<LangData> list = new ArrayList<>();
		for (Entry<String, LangData> set : v.entrySet()) {
			list.add(set.getValue());
		}
		sil.setList(list);
		return sil;
	}

}
