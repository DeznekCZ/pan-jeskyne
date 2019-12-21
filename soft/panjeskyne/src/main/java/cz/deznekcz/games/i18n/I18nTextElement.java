package cz.deznekcz.games.i18n;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "text")
@XmlAccessorType(XmlAccessType.NONE)
public class I18nTextElement {
	
	@XmlAttribute(name = "line", required = false)
	private boolean breakLine;
	
	public boolean isBreakLine() {
		return breakLine;
	}
	
	@XmlAttribute(name = "link", required = false)
	private String link;
	
	public boolean isLink() {
		return link != null && link.length() > 0;
	}
	
	public String getLink() {
		return link;
	}
	
	@XmlAttribute(name = "bolt", required = false)
	private boolean bolt;
	
	public boolean isBolt() {
		return bolt;
	}
	
	@XmlAttribute(name = "space", required = false)
	private int space;
	
	public int getSpace() {
		return space;
	}
	
	@XmlAttribute(name = "italic", required = false)
	private boolean italic;
	
	public boolean isItalic() {
		return italic;
	}
	
	@XmlAttribute(name = "expression", required = false)
	private String expression;
	
	public String getExpression() {
		return expression;
	}
	
	public boolean isExpression() {
		return expression != null && expression.length() > 0;
	}

	private String getExpressionValue() {
		return "{" + expression + "}";
	}
	
	@XmlValue
	private String text;

	public String getText() {
		return isExpression() ? getExpressionValue() : (text != null ? text : "");
	}

	public String convert() {
		StringBuilder sb = new StringBuilder(getText());
		
		if (isLink()) {
			sb.insert(0, "<a class=\"v-button-caption\" href=\""+getLink()+"\">");
			sb.append("</a>");
		}
		if (isBolt()) {
			sb.insert(0, "<b>");
			sb.append("</b>");
		}
		if (isItalic()) {
			sb.insert(0, "<i>");
			sb.append("</i>");
		}
		
		if (getSpace() > 0)
			for (int i = 0; i < getSpace(); i++) sb.insert(0,"&nbsp;");
		if (isBreakLine())
			sb.insert(0,"<br/>");
		
		return sb.toString();
	}
}
