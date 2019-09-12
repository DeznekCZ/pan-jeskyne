package cz.deznekcz.games.panjeskyne.utils;

import com.vaadin.ui.AbstractComponent;

public class ComponentPair<D, C extends AbstractComponent> {

	private C component;
	private D data;

	public ComponentPair(D data, C component) {
		this.data = data;
		this.component = component;
	}
	
	public void setData(D data) {
		this.data = data;
	}
	
	public C getComponent() {
		return component;
	}

	public D getData() {
		return data;
	}

}
