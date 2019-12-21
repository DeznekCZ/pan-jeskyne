package cz.deznekcz.games.panjeskyne.model.xml;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import cz.deznekcz.games.panjeskyne.data.LangData;

@XmlRootElement(name = "description")
@XmlAccessorType(XmlAccessType.NONE)
public class Description implements XmlSerialized {

	private static final long serialVersionUID = -5119938136784903864L;
	
	@XmlMixed
	@XmlAnyElement
	private List<Object> value;

	@XmlTransient
	private String finalValue;
	
	public Description() {
		
	}

	public synchronized String getValue() {
		if (value != null && finalValue != null) {
			StringBuffer buffer = new StringBuffer();
			
			for (Object object : value) {
				if (object instanceof Node) {
					try {
						StringWriter writer = new StringWriter();
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.transform(new DOMSource((Node) object), new StreamResult(writer));
						String string = writer.toString();
						buffer.append(string.substring(string.indexOf('>') + 1));
					} catch (TransformerFactoryConfigurationError | TransformerException e) {
						
					}
				} else {
					buffer.append(object);
				}
			}
			
			return buffer.toString();
		} else if (finalValue != null) {
			return finalValue;
		} else {
			return "";
		}
	}

	public synchronized void setValue(String value) {
		if (this.value == null) {
			this.value = new ArrayList<>();
		}
		this.value.clear();
		this.finalValue = value;
		this.value.add(value);
	}

}
