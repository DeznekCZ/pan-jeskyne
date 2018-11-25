package cz.panjeskyne.model.xml.adapter;

import cz.panjeskyne.model.xml.*;

public class BonusMapAdapter<I extends XmlMappable<String, I>, L extends ListType<I>> 
		extends ListToMapAdapter<String, I, L> {
	
	public static class KindStatistic
			extends BonusMapAdapter<KindStatisticBonus, KindStatisticBonuses> {}
	
	public static class Skill
			extends BonusMapAdapter<SkillStatisticBonus, SkillStatisticBonuses> {}
	
	public static class Level
			extends BonusMapAdapter<LevelStatisticBonus, LevelStatisticBonuses> {}
}
