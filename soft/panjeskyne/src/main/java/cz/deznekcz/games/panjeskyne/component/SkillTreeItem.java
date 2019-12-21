package cz.deznekcz.games.panjeskyne.component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import cz.deznekcz.games.events.SkillChanged;
import cz.deznekcz.games.panjeskyne.model.xml.XmlSerialized;
import cz.deznekcz.games.panjeskyne.service.SkillService;

public class SkillTreeItem implements XmlSerialized {

	public static enum Type {
		SKILL, GROUP
	}
	
	private String id;
	private Type type;
	private String group;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getName() {
		return type == Type.SKILL 
				? SkillService.getInstance().getSkill(id).getName()
				: SkillService.getInstance().getGroup(id).getName();
	}

	public String getDescription() {
		return type == Type.SKILL 
				? SkillService.getInstance().getSkill(id).getDescription()
				: SkillService.getInstance().getGroup(id).getDescription();
	}

	public boolean skillEdited(String id) {
		return this.id.equals(id);
	}
	
	@Override
	public String toString() {
		return getName();
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getGroup() {
		return group;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof SkillTreeItem && equals((SkillTreeItem) obj);
	}
	
	public boolean equals(SkillTreeItem obj) {
		return obj.type == type && obj.id.equals(id);
	}
	
	@Override
	public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		id = (String) in.readObject();
		type = (Type) in.readObject();
		group = (String) in.readObject();
	}
	
	@Override
	public void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(id);
		out.writeObject(type);
		out.writeObject(group);
	}
}
