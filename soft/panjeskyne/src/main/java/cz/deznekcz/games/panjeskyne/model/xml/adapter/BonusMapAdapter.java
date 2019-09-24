package cz.deznekcz.games.panjeskyne.model.xml.adapter;

import java.util.HashMap;

import cz.deznekcz.games.panjeskyne.model.xml.Bonus;
import cz.deznekcz.games.panjeskyne.model.xml.Bonuses;

public class BonusMapAdapter extends ListToMapAdapter<String, Bonus, Bonuses, Bonus> {

	@Override
	public Bonuses marshal(HashMap<String, Bonus> v) throws Exception {
		return build(Bonuses::new, v);
	}

}
