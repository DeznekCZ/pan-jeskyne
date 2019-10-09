package cz.deznekcz.games.panjeskyne.drdplus2.data;

import java.util.Map;

public interface HasInitStats {
	
	void setStatsAsMap(Map<String, Integer> map);
	
	Map<String, Integer> getStatsAsMap();
	
	default int getStat(String id) {
		return getStatsAsMap().getOrDefault(id, 0);
	}
	
	default void setStat(String id, int value) {
		getStatsAsMap().put(id, value);
	}
}
