package cz.deznekcz.games.panjeskyne.module;

import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.Maps;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import cz.deznekcz.games.panjeskyne.user.User;
import cz.deznekcz.games.panjeskyne.client.Services;
import cz.deznekcz.games.panjeskyne.model.xml.Character;
import cz.deznekcz.games.panjeskyne.model.xml.Statistic;
import cz.deznekcz.games.panjeskyne.service.CharacterService;
import cz.deznekcz.games.panjeskyne.service.KindService;
import cz.deznekcz.games.panjeskyne.service.SkillService;
import cz.deznekcz.games.panjeskyne.service.StatisticService;
import cz.deznekcz.games.panjeskyne.service.formula.Result;

public abstract class AModule {
	
	private static final Map<String, AModule> map = Maps.newHashMap();
	
	private String id;
	
	private StatisticService statisticService;
	private SkillService skillService;
	private KindService kindService;
	private CharacterService CharacterService;

	protected AModule(String id, String string) {
		this.id = id;
		AModule.map.put(this.id, this);
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

	public CharacterService getCharacterService() {
		return CharacterService;
	}
	
	public SkillService getSkillService() {
		return skillService;
	}
	
	public KindService getKindService() {
		return kindService;
	}
	
	public StatisticService getStatisticService() {
		return statisticService;
	}

	public TextField getStatisticAsField(String statisticId, Character character, boolean isFloat, Consumer<String> onEdit) {
		StatisticService service = Services.getStatisticService();
		Statistic statistic = service.getByCodename(statisticId);
		TextField textField = new TextField();
		textField.setCaption(statistic.getName());
		Result result = service.getValue(character, statistic);
		if (isFloat) {
			textField.setValue(result.getValue() + "");
		} else {
			textField.setValue((long) result.getValue() + "");
		}
		textField.setReadOnly(onEdit == null);
		return textField;
	}
}
