package cz.deznekcz.games.panjeskyne.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HasSkills<T extends ICharSkill> {
	
	void setSkillsAsMap(Map<String, T> map);
	
	Map<String, T> getSkillsAsMap();
	
	default List<T> getSkills() {
		return new ArrayList<T>( getSkillsAsMap().values() ); 
	}
	
	default T getSkill(String id) {
		return getSkillsAsMap().getOrDefault(id, null);
	}
	
	default void setSkill(String id, T value) {
		getSkillsAsMap().put(id, value);
	}
}
