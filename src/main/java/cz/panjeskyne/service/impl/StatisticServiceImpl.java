package cz.panjeskyne.service.impl;

import org.springframework.stereotype.Service;

import cz.panjeskyne.form.StatisticForm;
import cz.panjeskyne.model.Statistic;
import cz.panjeskyne.service.AbstractBaseService;
import cz.panjeskyne.service.StatisticService;

@Service
public class StatisticServiceImpl extends AbstractBaseService<Statistic>
		implements StatisticService {

	@Override
	public void fillModelFromForm(StatisticForm form, Statistic model) {
		model.setId(form.getId());
		model.setName(form.getName());
	}

	@Override
	public void fillFormFromModel(StatisticForm form, Statistic model) {
		form.setId((Long) model.getId());
		form.setName(model.getName());
	}

}
