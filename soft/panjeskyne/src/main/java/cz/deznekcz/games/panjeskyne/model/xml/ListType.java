package cz.deznekcz.games.panjeskyne.model.xml;

import java.util.List;

public interface ListType<T> extends XmlSerialized {
	List<T> getList();
}
