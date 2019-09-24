package cz.deznekcz.games.panjeskyne.model.xml.adapter;

import java.util.HashMap;

import cz.deznekcz.games.panjeskyne.model.xml.Kind;
import cz.deznekcz.games.panjeskyne.model.xml.Kinds;

public class KindMapAdapter extends ListToMapAdapter<String, Kind, Kinds, Kind> {

	@Override
	public Kinds marshal(HashMap<String, Kind> v) throws Exception {
		return build(Kinds::new, v);
	}

}
