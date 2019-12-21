package cz.deznekcz.games.events;

import com.google.common.eventbus.Subscribe;

public class EventTemplate implements Event {

	private static final long serialVersionUID = 1L;
	
	public static interface Accept extends Event.AcceptTemplate {
		
		void templateAccept();
		
		@Subscribe
		default void event(EventTemplate event) {
			getUI().access(() -> this.templateAccept());
		}
	}
}
