package cz.deznekcz.games.i18n;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class I18NText {

	@XmlAttribute(name = "lang")
	private String lang;

	@XmlElement(name = "text")
	List<I18nTextElement> texts;
	
	public String getLang() {
		return lang;
	}
	
	public List<I18nTextElement> getTexts() {
		return texts;
	}

	public String convert() {
		StringBuilder ft = new StringBuilder("");
		boolean exp = false;
		
		for (I18nTextElement element : getTexts()) {
			ft.append(element.convert());
		}
		
		return ft.toString();
	}

}
