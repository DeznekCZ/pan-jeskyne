package cz.panjeskyne.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SkillStatisticBonuses implements ListType<SkillStatisticBonus> {
	@XmlElement(name = "bonus")
	private List<SkillStatisticBonus> bonuses;

	@Override
	public List<SkillStatisticBonus> getList() {
		return bonuses;
	}
}
