package cz.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class KindStatisticBonuses implements ListType<KindStatisticBonus> {
	@XmlElement(name = "bonus")
	private List<KindStatisticBonus> bonuses;

	@Override
	public List<KindStatisticBonus> getList() {
		return bonuses;
	}
}
