package cz.deznekcz.util.xml;

import java.util.ArrayList;
import java.util.List;

import cz.deznekcz.games.panjeskyne.service.formula.Pair;

/**
 * Represent all XML pair tags (excluding root element)
 * 
 * @author Zdenek Novotny (DeznekCZ)
 *
 * @param <P> Class type of parent implementation (parent in parent child instances relation)
 * 
 * @see XMLRoot XMLRoot is top parent
 * @see XMLPairTag XMLPairTag is closses parent or child
 * @see XMLSingleTag XMLRoot is child
 */
public class XMLPairTag<P> extends XMLPairTagBase<P> {

	protected XMLPairTag(String name, P parent, boolean expanded) {
		super(name, parent, expanded);
	}

	@Override
	public XMLSingleTag<XMLPairTag<P>> newSingleTag(String name) {
		return new XMLSingleTag<XMLPairTag<P>>(name, this);
	}

	@Override
	public XMLPairTag<XMLPairTag<P>> newPairTag(String name) {
		return new XMLPairTag<XMLPairTag<P>>(name, this, true);
	}

	@Override
	public XMLPairTag<XMLPairTag<P>> newPairTag(String name, boolean expanded) {
		return new XMLPairTag<XMLPairTag<P>>(name, this, expanded);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<XMLSingleTag<XMLPairTag<P>>> getSingleTag(String name) {
		List<XMLSingleTag<XMLPairTag<P>>> list = new ArrayList<>(children.size());
		if (children.containsKey(name)) for (XMLElement<?, ?> e : children.get(name)) {
			if (e.name.equals(name) && e instanceof XMLSingleTag) list.add((XMLSingleTag<XMLPairTag<P>>) e);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<XMLPairTag<XMLPairTag<P>>> getPairTag(String name) {
		List<XMLPairTag<XMLPairTag<P>>> list = new ArrayList<>(children.size());
		if (children.containsKey(name)) for (XMLElement<?, ?> e : children.get(name)) {
			if (e.name.equals(name) && e instanceof XMLPairTag) list.add((XMLPairTag<XMLPairTag<P>>) e);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<XMLElement<XMLPairTag<P>, ?>> getBoothTag(String name) {
		List<XMLElement<XMLPairTag<P>, ?>> list = new ArrayList<>(children.size());
		if (children.containsKey(name)) for (XMLElement<?, ?> e : children.get(name)) {
			if (e.name.equals(name)) list.add((XMLElement<XMLPairTag<P>, ?>) e);
		}
		return list;
	}

	@Override
	public XMLPairTag<P> appendText(String text) {
		super.text += text;
		return this;
	}

	@Override
	public XMLPairTag<P> appendTextCDATA(String text) {
		super.text += String.format("<![CDATA[%s]]>", text);
		return this;
	}

	@Override
	public XMLPairTag<P> setText(String text) {
		super.text = text;
		return this;
	}

	@Override
	public XMLPairTag<P> setTextCDATA(String text) {
		super.text = String.format("<![CDATA[%s]]>", text);
		return this;
	}
	
	@Override
	public XMLPairTag<P> addAttribute(String name, String value) {
		attributes.add(new Pair<String, String>(name, value));
		return this;
	}
	
	@Override
	public XMLPairTag<P> setComment(String comment) {
		super.comment = String.format("<!-- %s -->", comment);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("<pairTag=%s,\nattributes=%s,\nchildren=%s\n>",name,attributes,children);
	}

}
