package cz.deznekcz.util.xml;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cz.deznekcz.games.panjeskyne.service.formula.Pair;


/**
 *
 * @author Zdenek Novotny (DeznekCZ)
 *
 * Represent base of all XML tags
 *
 * @param <P> Class type of parent implementation (parent in parent child instances relation)
 * @param <T> Class type of final implementation
 *
 * @see XMLSingleTag
 * @see XMLPairTag
 * @see XMLRoot
 */
public abstract class XMLElement<P, T extends XMLElement<P, T>> {

	protected P parent;
	protected String name;
	protected String text;
	protected String comment;
	protected boolean expanded;

	protected List<Pair<String, String>> attributes;
	protected Map<String,List<XMLElement<?,?>>> children;

	protected XMLElement(String name, P parent, boolean expanded) {
		if (parent instanceof XMLPairTagBase) {
			XMLPairTagBase<?> cast = (XMLPairTagBase<?>) parent;
			if (!cast.children.containsKey(name))
				cast.children.put(name, new LinkedList<>());
			cast.children.get(name).add(this);
		}

		this.parent = parent;
		this.name = name;
		this.text = "";
		this.comment = "";
		this.expanded = expanded;

		this.children = new HashMap<>();
		this.attributes = new LinkedList<>();
	}

	/**
	 * Returns parent instance
	 * @return instance of {@link XMLElement#P}
	 */
	public P close() {
		return parent;
	}

	/**
	 * Adds an attribute to XML element
	 * @param name name of attribute
	 * @param value value of attribute
	 * @return this instance of {@link XMLElement#T} (for builder initialization see {@link XML})
	 */
	public abstract T addAttribute(String name, String value);
	/**
	 * Sets comment for XML element
	 * @param comment comment value
	 * @return this instance of {@link XMLElement#T} (for builder initialization see {@link XML})
	 */
	public abstract T setComment(String comment);

	/**
	 * Returns value of comment
	 * @return comment value
	 */
	public String getComment() {
		return comment.substring(4, comment.length() - 3).trim();
	}

	/**
	 * Method returns representation of XML document for a text file
	 * @param indent enable writing of indentations
	 * @param parentExpanded defines using of indentation for child elements
	 */
	public void write(Appendable stream, int indent, boolean parentExpanded) throws IOException {
		if (comment != null && comment.length() > 0) {
			if (parentExpanded) {
				indent(stream, indent);
			}
			stream.append(comment);
		}
		if (parentExpanded) indent(stream, indent);
		stream.append("<" + name);
		attributes(stream, indent + 2);

		if (this instanceof XMLSingleTag) {
			stream.append(" />");
		} else {
			stream.append(" >");
			for (List<XMLElement<?,?>> elementList : children.values()) {
				for (XMLElement<?,?> xmlElem : elementList) {
					xmlElem.write(stream, indent + 1, parentExpanded && expanded);
				}
			}

			if (text != null && text.length() > 0) {
				if (parentExpanded && expanded) {
					stream.append('\n');
				}
				stream.append(text);
			}

			if (parentExpanded && expanded) {
				indent(stream, indent);
			}
			stream.append(String.format("</%s>", name));
		}
	}

	private void indent(Appendable stream, int indent) throws IOException {
		stream.append('\n');
		for (int i = 0; i < indent; i++) {
			stream.append("  ");
		}
	}

	private void attributes(Appendable stream, int indent) throws IOException {
		int counter = 0;
		for (Pair<String, String> pair : attributes) {
			if (expanded && (counter++ & 0xFFFFFFFD) == 0xFFFFFFFD) {
				indent(stream, indent);
			}
			stream.append(String.format(" %s=\"%s\"", pair.getKey(), pair.getValue()));
		}
	}
}
