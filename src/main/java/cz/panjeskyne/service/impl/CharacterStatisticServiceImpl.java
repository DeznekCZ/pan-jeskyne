package cz.panjeskyne.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.panjeskyne.dao.CharacterDAO;
import cz.panjeskyne.dao.StatisticDAO;
import cz.panjeskyne.form.CharacterStatisticForm;
import cz.panjeskyne.model.CharacterStatistic;
import cz.panjeskyne.service.AbstractBaseService;
import cz.panjeskyne.service.CharacterStatisticService;

@Service
public class CharacterStatisticServiceImpl extends AbstractBaseService<CharacterStatistic> implements CharacterStatisticService {

	@Autowired
	private CharacterDAO characterDao;

	@Autowired
	private StatisticDAO statisticDao;
	
	@Override
	public void fillModelFromForm(CharacterStatisticForm form, CharacterStatistic model) {
		model.setId(form.getId());
		model.setValue(form.getValue());
		model.setCharacter(characterDao.getReference(form.getCharacterId()));
		model.setStatistic(statisticDao.getReference(form.getStatisticId()));
	}

	@Override
	public void fillFormFromModel(CharacterStatisticForm form, CharacterStatistic model) {
		form.setId((Long)model.getId());
		form.setValue(model.getValue());
		form.setCharacterId((Long)model.getCharacter().getId());
		form.setStatisticId((Long)model.getStatistic().getId());
		form.setName(model.getStatistic().getName());
	}
}
