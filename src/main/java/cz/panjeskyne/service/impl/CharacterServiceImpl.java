package cz.panjeskyne.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.panjeskyne.form.CharacterForm;
import cz.panjeskyne.form.CharacterStatisticForm;
import cz.panjeskyne.model.Character;
import cz.panjeskyne.model.Statistic;
import cz.panjeskyne.model.CharacterStatistic;
import cz.panjeskyne.service.AbstractBaseService;
import cz.panjeskyne.service.CharacterService;
import cz.panjeskyne.service.CharacterStatisticService;
import cz.panjeskyne.service.StatisticService;

@Service
public class CharacterServiceImpl extends AbstractBaseService<Character> implements CharacterService {

	@Autowired
	private CharacterStatisticService characterStatisticService;

	@Autowired
	private StatisticService statisticService;

	@Override
	public void fillModelFromForm(CharacterForm form, Character model) {
		model.setId(form.getId());
		model.setName(form.getName());
		List<CharacterStatistic> statisticsModel = model.getStatistics();
		for(CharacterStatisticForm characterStatForm : form.getStatistics()) {
			Long statistikaPostavyId = characterStatForm.getId();
			CharacterStatistic characterStatModel = model.getStatistikaPostavy(statistikaPostavyId);
			if(characterStatModel == null) {
				characterStatModel = new CharacterStatistic();
				characterStatisticService.fillModelFromForm(characterStatForm, characterStatModel);
				characterStatModel.setCharacter(model);
				statisticsModel.add(characterStatModel);
			} else {
				characterStatisticService.fillModelFromForm(characterStatForm, characterStatModel);
				characterStatModel.setCharacter(model);
			}
		}
	}
	
	@Override
	public void fillFormFromModel(CharacterForm form, Character model) {
		form.setId((Long)model.getId());
		form.setName(model.getName());
		form.setStatistics(prepareCharacterStatistics(model));
	}

	public List<CharacterStatisticForm> prepareCharacterStatistics(Character model) {
		List<Statistic> statistics = statisticService.getAll();
		Map<Long, CharacterStatisticForm> forms = new LinkedHashMap<>();
		// vytvorit formulare pro vsechny typy statistik
		for(Statistic stat : statistics) {
			CharacterStatisticForm characterStatForm = new CharacterStatisticForm();
			characterStatForm.setStatisticId((Long)stat.getId());
			characterStatForm.setCharacterId((Long)model.getId());
			characterStatForm.setName(stat.getName());
			forms.put((Long)stat.getId(), characterStatForm);
		}
		// vyplnit formulare podle statistik postavy z DB
		for(CharacterStatistic characterStatModel : model.getStatistics()) {
			CharacterStatisticForm characterStatForm = forms.get(characterStatModel.getStatistic().getId());
			characterStatisticService.fillFormFromModel(characterStatForm, characterStatModel);
		}
		return new ArrayList<>(forms.values());
	}
}
