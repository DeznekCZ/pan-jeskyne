package cz.deznekcz.games.panjeskyne.component;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import cz.deznekcz.games.events.SkillChanged;

public class SkillView extends Panel implements SkillChanged.AcceptEdited {

	private static final long serialVersionUID = 1L;
	
	private SkillTreeItem item;

	private Label text;

	private boolean saved;

	public SkillView(SkillTreeItem item) {
		this.item = item;
		this.saved = true;
		this.setVisible(true);
		
		this.text = new Label("", ContentMode.HTML);
		this.text.setWidth(100, Unit.PERCENTAGE);
		
		setContent(text);
		
		refreshValues();
		
		registerAccept();
	}
	
	@Override
	public void skillEdited(String id) {
		if (item.getId().equals(id)) {
			refreshValues();
		}
	}

	public void refreshValues() {
		setCaption(item.getName());
		text.setValue(item.getDescription());
	}

	public void save() {
		saved = true;
	}

	public boolean isSaved() {
		return saved;
	}
}
