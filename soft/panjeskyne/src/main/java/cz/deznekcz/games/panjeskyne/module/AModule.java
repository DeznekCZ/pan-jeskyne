package cz.deznekcz.games.panjeskyne.module;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.Maps;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import cz.deznekcz.games.panjeskyne.user.User;
import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Statistic;
import cz.deznekcz.games.panjeskyne.service.CharacterService;
import cz.deznekcz.games.panjeskyne.service.KindService;
import cz.deznekcz.games.panjeskyne.service.RaceService;
import cz.deznekcz.games.panjeskyne.service.SkillGroupService;
import cz.deznekcz.games.panjeskyne.service.SkillService;
import cz.deznekcz.games.panjeskyne.service.StatisticService;
import cz.deznekcz.games.panjeskyne.service.formula.Result;

public abstract class AModule implements Serializable {
	
	private static final Map<String, AModule> map = Maps.newHashMap();
	
	private String id;
	
	private StatisticService statisticService;
	private SkillGroupService skillGroupService;
	private SkillService skillService;
	private RaceService raceService;
	private KindService kindService;
	private CharacterService characterService;
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(id);
		
		storeModuleToCache(out);
	}
	
	protected abstract void storeModuleToCache(ObjectOutputStream out);

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		id = (String) in.readObject();

		loadServices();
		
		readModuleFromCache(in);
	}
	
	protected abstract void readModuleFromCache(ObjectInputStream in);

	@SuppressWarnings("unused")
	private void readObjectNoData() throws ObjectStreamException {
		throw new ObjectStreamException() {
			private static final long serialVersionUID = 4941208986572404688L;
		};
	}

	protected AModule(String id, String string) {
		this.id = id;
		AModule.map.put(this.id, this);
		
		loadServices();
	}
	
	private void loadServices() {
//		statisticService = new StatisticService(this);
//		skillService = new SkillService(this);
//		skillGroupService = skillService.getSkillGroupService();
//		kindService = new KindService(this);
//		raceService = kindService.getRaceService();
//		characterService = new CharacterService(this);
	}

	public String getId() {
		return id;
	}
	
	public static Map<String, AModule> getModules() {
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends AModule> T getModule(String id) throws ModuleException {
		if (getModules().containsKey(id)) {
			AModule m = getModules().get(id);
			try {
				return (T) getModules().get(id);
			} catch (ClassCastException e) {
				throw new ModuleNotMatchException(id, m.getClass());
			}
		} else {
			throw new ModuleNotFoundException(id);
		}
	}

	public abstract Window getCharacterCreationScreen(Runnable onCreated);
	public abstract Window getCharacterPreviewScreen(User viewingUser, Character character);
	public abstract Window getCharacterEditScreen(Runnable onSaved, Character character);
	
	public StatisticService getStatisticService() {
		return statisticService;
	}
	
	public SkillGroupService getSkillGroupService() {
		return skillGroupService;
	}
	
	public SkillService getSkillService() {
		return skillService;
	}
	
	public RaceService getRaceService() {
		return raceService;
	}
	
	public KindService getKindService() {
		return kindService;
	}

	public CharacterService getCharacterService() {
		return characterService;
	}

	public TextField getStatisticAsField(String statisticId, Character character, boolean isFloat, Consumer<String> onEdit) {
		StatisticService service = getStatisticService();
		Statistic statistic = service.getByCodename(statisticId);
		TextField textField = new TextField();
		textField.setData(statistic);
		textField.setCaption(statistic.getName());
		Result result = service.getValue(character, statistic);
		if (isFloat) {
			textField.setValue(result.getValue() + "");
		} else {
			textField.setValue((long) result.getValue() + "");
		}
		textField.setReadOnly(onEdit == null);
		if (onEdit != null) textField.addValueChangeListener(event -> {
			onEdit.accept(event.getValue());
		});
		return textField;
	}

	public static interface InfoGetter<T> {
		T get() throws Throwable; 
	}
	
	public static <T> T tryGet(T defaultValue, InfoGetter<T> object) {
		try {
			return object.get();
		} catch (Throwable e) {
			e.printStackTrace(System.out);
			return defaultValue;
		}
	}

	public <L extends ComponentContainer, C extends Component> void add(L l, C c, Consumer<C> onAddAction) {
		l.addComponent(c);
		onAddAction.accept(c);
	}

	public static String numberToIII(int level) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < level; i++) {
			stringBuilder.append('I');
		}
		return stringBuilder.toString();
	}
}
