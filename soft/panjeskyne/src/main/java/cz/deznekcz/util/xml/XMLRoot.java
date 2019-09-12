package cz.deznekcz.util.xml;

import java.util.ArrayList;
import java.util.List;

import cz.deznekcz.games.panjeskyne.service.formula.Pair;

/**
 * Represents root XML element
 * 
 * @author Zdenek Novotny (DeznekCZ)
 * 
 * @see XML XML is OWNER
 * @see XMLPairTag XMLPairTag is CHILD
 * @see XMLSingleTag XMLRoot is CHILD
 */
public class XMLRoot extends XMLPairTagBase<XML> {

	protected XMLRoot(String name, XML parent) {
		super(name, parent, true);
	}

	@Override
	public XML close() {
		return parent;
	}

	@Override
	public XMLSingleTag<XMLRoot> newSingleTag(String name) {
		return new XMLSingleTag<XMLRoot>(name, this);
	}

	@Override
	public XMLPairTag<XMLRoot> newPairTag(String name) {
		return new XMLPairTag<>(name, this, true);
	}
	
	@Override
	public XMLRoot setText(String text) {
		super.text = text;
		return this;
	}

	@Override
	public XMLPairTag<XMLRoot> newPairTag(String name, boolean expanded) {
		return new XMLPairTag<>(name, this, expanded);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<XMLSingleTag<XMLRoot>> getSingleTag(String name) {
		List<XMLSingleTag<XMLRoot>> list = new ArrayList<>(children.size());
		if (children.containsKey(name)) for (XMLElement<?, ?> e : children.get(name)) {
			if (e.name.equals(name) && e instanceof XMLSingleTag) list.add((XMLSingleTag<XMLRoot>) e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<XMLPairTag<XMLRoot>> getPairTag(String name) {
		List<XMLPairTag<XMLRoot>> list = new ArrayList<>(children.size());
		if (children.containsKey(name)) for (XMLElement<?, ?> e : children.get(name)) {
			if (e.name.equals(name) && e instanceof XMLPairTag) list.add((XMLPairTag<XMLRoot>) e);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<XMLElement<XMLRoot, ?>> getBoothTag(String name) {
		List<XMLElement<XMLRoot, ?>> list = new ArrayList<>(children.size());
		if (children.containsKey(name)) for (XMLElement<?, ?> e : children.get(name)) {
			if (e.name.equals(name)) list.add((XMLElement<XMLRoot, ?>) e);
		}
		return list;
	}

	@Override
	public XMLRoot appendText(String text) {
		super.text += text;
		return this;
	}

	@Override
	public XMLRoot appendTextCDATA(String text) {
		super.text += String.format("<![CDATA[%s]]>", text);
		return this;
	}
	
	@Override
	public XMLRoot setTextCDATA(String text) {
		super.text = String.format("<![CDATA[%s]]>", text);
		return this;
	}
	
	@Override
	public XMLRoot addAttribute(String name, String value) {
		attributes.add(new Pair<String, String>(name, value));
		return this;
	}
	
	@Override
	public XMLRoot setComment(String comment) {
		super.comment = String.format("<!-- %s -->", comment);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("<rootTag=%s,\nattributes=%s,\nchildren=%s\n>",name,attributes,children);
	}
}
