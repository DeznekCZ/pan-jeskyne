package cz.deznekcz.games.panjeskyne.data;

import java.util.List;

import cz.deznekcz.games.panjeskyne.model.xml.ListType;

public class SimpleListType<T> implements ListType<T> {

	private static final long serialVersionUID = 3751816617410321557L;
	
	private List<T> list;

	@Override
	public List<T> getList() {
		return list;
	}

	@Override
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
