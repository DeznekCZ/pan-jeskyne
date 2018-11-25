package cz.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LevelStatisticBonuses implements ListType<LevelStatisticBonus> {
	@XmlElement(name = "bonus")
	private List<LevelStatisticBonus> bonuses;

	@Override
	public List<LevelStatisticBonus> getList() {
		return bonuses;
	}
}
