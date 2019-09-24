package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.List;

public interface ListType<T> extends XmlSerialized {
	/**
	 * For un-marshaling of XML object
	 * @return list of elements
	 */
	List<T> getList();
	/**
	 * For marshaling of XML object
	 * @param list list of elements
	 */
	void setList(List<T> list);
}
