package cz.deznekcz.games.panjeskyne.pages;

import java.util.HashMap;
import java.util.function.Consumer;

import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class Dialogs {
	public static enum Type {
		YES_NO_CANCEL, YES_NO, OK_CANCEL, OK
	}

	private Type type;
	
	private String message;
	private String title;
	private String yes;
	private String no;
	private String cancel;

	private Consumer<Dialogs> yesAction;
	private Consumer<Dialogs> noAction;
	private Consumer<Dialogs> cancelAction;
	
	private Component content;

	private HashMap<Type, String> titles;
	private HashMap<Type, String> messages;
	private HashMap<Type, String> yess;
	private HashMap<Type, String> nos;
	private HashMap<Type, String> cancels;

	private UI ui;

	private Window window;

	private ClickEvent clickEvent;
	
	public Dialogs() {
		type = Type.OK;
		
		titles = new HashMap<>();
		titles.put(Type.YES_NO_CANCEL, "Potvrdit změny!");
		titles.put(Type.YES_NO, "Otázka?");
		titles.put(Type.OK_CANCEL, "Pokračovat?");
		titles.put(Type.OK, "Info");
		
		messages = new HashMap<>();
		messages.put(Type.YES_NO_CANCEL, "Ano, ne nebo zrušit?");
		messages.put(Type.YES_NO, "Ano nebo ne?");
		messages.put(Type.OK_CANCEL, "Pokračovat nebo zrušit?");
		messages.put(Type.OK, "Pouze to odklepněte.");
		
		yess = new HashMap<>();
		yess.put(Type.YES_NO_CANCEL, "Ano");
		yess.put(Type.YES_NO, "Ano");
		yess.put(Type.OK_CANCEL, "OK");
		yess.put(Type.OK, "OK");
		
		nos = new HashMap<>();
		nos.put(Type.YES_NO_CANCEL, "Ne");
		nos.put(Type.YES_NO, "Ne");
		
		cancels = new HashMap<>();
		cancels.put(Type.YES_NO_CANCEL, "Zrušit");
		cancels.put(Type.OK_CANCEL, "Zrušit");
	}

	public static Dialogs ask(Type type) {
		return new Dialogs().type(type);
	}

	private Dialogs type(Type type) {
		this.type = type == null ? Type.OK : type;
		return this;
	}

	public Dialogs title(String title) {
		this.title = title;
		return this;
	}

	public Dialogs message(String message) {
		this.message = message;
		return this;
	}

	public Dialogs ok(String ok) {
		return yes(ok);
	}

	public Dialogs yes(String yes) {
		this.yes = yes;
		return this;
	}

	public Dialogs no(String no) {
		this.no = no;
		return this;
	}

	public Dialogs content(Component content) {
		this.content = content;
		return this;
	}

	public Dialogs cancel(String cancel) {
		this.cancel = cancel;
		return this;
	}

	public Dialogs onOk(Runnable okAction) {
		return onOk(dialog -> okAction.run());
	}

	public Dialogs onOk(Consumer<Dialogs> okAction) {
		return onYes(okAction);
	}

	public Dialogs onYes(Runnable okAction) {
		return onYes(dialog -> okAction.run());
	}

	public Dialogs onYes(Consumer<Dialogs> yesAction) {
		this.yesAction = yesAction;
		return this;
	}

	public Dialogs onNo(Runnable noAction) {
		return onNo(dialog -> noAction.run());
	}

	public Dialogs onNo(Consumer<Dialogs> noAction) {
		this.noAction = noAction;
		return this;
	}

	public Dialogs onCancel(Runnable cancelAction) {
		return onCancel(dialog -> cancelAction.run());
	}

	public Dialogs onCancel(Consumer<Dialogs> cancelAction) {
		this.cancelAction = cancelAction;
		return this;
	}
	
	public void show() {
		window = new Window(text(title, titles));
		window.setModal(true);
		
		VerticalLayout v = new VerticalLayout();
		if (content != null) v.addComponent(content);
		else v.addComponent(new Label(text(message, messages)));
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setWidth(100, Unit.PERCENTAGE);
		v.addComponent(buttons);
		
		window.setContent(v);

		// Yes, Ok
		buttons.addComponent(new Button(text(yes, yess), event(yesAction)));
		// No
		switch (type) {
		case YES_NO_CANCEL:
		case YES_NO:
			buttons.addComponent(new Button(text(no, nos), event(noAction)));
			break;
		default:
			break;
		}
		//cancel
		switch (type) {
		case YES_NO_CANCEL:
		case OK_CANCEL:
			buttons.addComponent(new Button(text(cancel, cancels), event(cancelAction)));
			break;
		default:
			break;
		}
		
		// Close
		switch (type) {
		case YES_NO:
		case OK:
			window.setClosable(false);
			break;
		case OK_CANCEL:
		case YES_NO_CANCEL:
			if (cancelAction != null)
				window.addCloseListener(e -> cancelAction.accept(this));

		default:
			break;
		}
		
		UI.getCurrent().addWindow(window);
	}

	private ClickListener event(Consumer<Dialogs> event) {
		if (event != null)
			return (e) -> {
				window.close();
				event.accept(this);
			};
		return (e) -> { window.close(); };
	}

	private String text(String text, HashMap<Type, String> texts) {
		return text == null ? texts.get(type) : text;
	}

	public static void info(String title, String message) {
		new Dialogs().type(Type.OK).title(title).message(message).show();
	}

	public Dialogs clickEvent(ClickEvent clickEvent) {
		this.clickEvent = clickEvent;
		return this;
	}
	
	public ClickEvent getClickEvent() {
		return clickEvent;
	}
}
