package cz.deznekcz.games.events;

import com.google.common.eventbus.Subscribe;

public class SkillChanged implements Event {

	private static final long serialVersionUID = 1L;
	
	private final String id;
	private final ChangeType type;
	
	public SkillChanged(String id, ChangeType type) {
		this.id = id;
		this.type = type;
	}

	public static interface Accept extends Event.AcceptTemplate {

		void skillAdded(String id);
		void skillEdited(String id);
		void skillRemoved(String id);
		
		@Subscribe
		default void event(SkillChanged event) {
			switch (event.type) {
			case ADD:
				getUI().access(() -> this.skillAdded(event.id));
				break;
			case EDIT:
				getUI().access(() -> this.skillEdited(event.id));
				break;
			case REMOVE:
				getUI().access(() -> this.skillRemoved(event.id));
				break;

			default:
				break;
			}
			
		}
	}

	public static interface AcceptAdded extends Event.AcceptTemplate {

		void skillAdded(String id);
		
		@Subscribe
		default void event(SkillChanged event) {
			if (event.type == ChangeType.ADD)
				getUI().access(() -> this.skillAdded(event.id));
		}
	}

	public static interface AcceptEdited extends Event.AcceptTemplate {

		void skillEdited(String id);
		
		@Subscribe
		default void event(SkillChanged event) {
			if (event.type == ChangeType.EDIT)
				getUI().access(() -> this.skillEdited(event.id));
		}
	}

	public static interface AcceptRemoved extends Event.AcceptTemplate {

		void skillRemoved(String id);
		
		@Subscribe
		default void event(SkillChanged event) {
			if (event.type == ChangeType.REMOVE)
				getUI().access(() -> this.skillRemoved(event.id));
		}
	}
}
