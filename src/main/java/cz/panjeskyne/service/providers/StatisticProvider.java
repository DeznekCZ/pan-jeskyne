package cz.panjeskyne.service.providers;

import java.util.HashMap;
import java.util.List;

public class StatisticProvider {
	public static final HashMap<String, List<IChangeHandler>> cache = new HashMap<>();
}
