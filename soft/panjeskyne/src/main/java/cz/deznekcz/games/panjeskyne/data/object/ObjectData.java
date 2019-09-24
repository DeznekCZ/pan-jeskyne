package cz.deznekcz.games.panjeskyne.data.object;

import cz.deznekcz.games.panjeskyne.model.xml.XmlMappable;

public interface ObjectData<T, R> extends XmlMappable<String, T> {
	public R getRawValue();
}
