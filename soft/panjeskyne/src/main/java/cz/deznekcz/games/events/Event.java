package cz.deznekcz.games.events;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.UI;

import cz.deznekcz.serialized.ObjectReader;
import cz.deznekcz.serialized.ObjectWriter;
import cz.deznekcz.serialized.Serialized;

public interface Event extends Serialized {

	public static interface AcceptTemplate {
		UI getUI();
		
		default void registerAccept() {
			Events.getInstance().register(this);
		}
		default void unregisterAccept() {
			Events.getInstance().unregister(this);
		}
	}

	@Override
	default void load(ObjectReader reader) throws IOException, ClassNotFoundException {
		HashMap<String, Object> data = reader.read();
		
		boolean b = false;
		for (Entry<String, Object> entry : data.entrySet()) {
			try {
				Field field = getClass().getField(entry.getKey());
				b = field.isAccessible();
				field.setAccessible(true);
				try {
					field.set(this, entry.getValue());
				} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
					e.printStackTrace();
				}
				field.setAccessible(b);
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	default void store(ObjectWriter writer) throws IOException {
		Field[] fields = getClass().getDeclaredFields();
		HashMap<String, Object> data = new HashMap<>();
		
		boolean b = false;
		for (Field field : fields) {
			b = field.isAccessible();
			field.setAccessible(true);
			try {
				data.put(field.getName(), field.get(this));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			field.setAccessible(b);
		}
		
		writer.add(data);
	}

	static void send(Event event) {
		Events.getInstance().post(event);
	}
}
