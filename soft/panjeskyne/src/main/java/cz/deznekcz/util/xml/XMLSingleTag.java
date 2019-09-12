package cz.deznekcz.util.xml;

import cz.deznekcz.games.panjeskyne.service.formula.Pair;

/**
 * Represents single tag elements
 * 
 * @author Zdenek Novotny (DeznekCZ)
 *
 * @param <P> Class type of parent implementation (parent in parent child instances relation)
 * 
 * @see XMLRoot XMLRoot is top parent
 * @see XMLPairTag XMLPairTag is closses parent
 */
public class XMLSingleTag<P> extends XMLElement<P, XMLSingleTag<P>> {

	protected XMLSingleTag(String name, P root) {
		super(name, root, true);
	}

	@Override
	public XMLSingleTag<P> addAttribute(String name, String value) {
		attributes.add(new Pair<String, String>(name, value));
		return this;
	}
	
	@Override
	public XMLSingleTag<P> setComment(String comment) {
		super.comment = String.format("<!-- %s -->", comment);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("<singleTag=%s,\nattributes=%s,\n>",name,attributes);
	}

}
