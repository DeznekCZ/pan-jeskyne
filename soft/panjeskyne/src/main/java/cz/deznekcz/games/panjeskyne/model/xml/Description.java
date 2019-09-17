package cz.deznekcz.games.panjeskyne.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "desc")
@XmlAccessorType(XmlAccessType.FIELD)
public class Description implements XmlSerialized {

	private static final long serialVersionUID = -5119938136784903864L;
	
	@XmlValue
	private String text;
	
	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
