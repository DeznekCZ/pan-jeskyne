package cz.deznekcz.games.panjeskyne.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import cz.deznekcz.games.panjeskyne.model.xml.ListType;

@XmlAccessorType(XmlAccessType.FIELD)
public class LangDataList implements ListType<LangData> {

	private static final long serialVersionUID = 6134055855924368958L;
	
	@XmlElement(name = "lang")
	private List<LangData> list;
	
	@Override
	public List<LangData> getList() {
		return list;
	}

	/** Marshaler */
	public void setList(List<LangData> list) {
		this.list = list;
	}
}
