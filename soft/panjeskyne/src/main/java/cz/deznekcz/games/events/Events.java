package cz.deznekcz.games.events;

import com.google.common.eventbus.EventBus;

public class Events extends EventBus {
	
	private static Events instance;
	
	public static Events getInstance() {
		if (instance == null) {
			instance = new Events();
		}
		return instance;
	}
	
	@Override
	public void unregister(Object object) {
		try {
			super.unregister(object);
		} catch (Exception e) {
			// ingore unremoved
		}
		
	}
}
