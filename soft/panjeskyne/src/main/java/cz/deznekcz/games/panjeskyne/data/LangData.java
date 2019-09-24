package cz.deznekcz.games.panjeskyne.data;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import cz.deznekcz.games.panjeskyne.model.xml.XmlMappable;
import cz.deznekcz.games.panjeskyne.model.xml.XmlSerialized;

@XmlRootElement(name = "lang")
public class LangData implements XmlMappable<String, LangData>, XmlSerialized {

	private static final long serialVersionUID = 1696187738427797043L;
	
	@XmlAttribute(name = "id")
	private String id;
	
	@XmlMixed
	@XmlAnyElement
	private List<Object> value;
	
	public LangData() {
		this.id = "---";
	}

	@Override
	public String getKey() {
		return id;
	}

	@Override
	public LangData getValue() {
		return this;
	}

	public String getStringValue() {
		if (value != null) {
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
		} else {
			return "---";
		}
	}
}
