package cz.panjeskyne.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.panjeskyne.form.StatisticForm;
import cz.panjeskyne.model.Statistic;
import cz.panjeskyne.service.StatisticService;

@Controller
@RequestMapping(StatisticController.BASE_PATH)
public class StatisticController extends AbstractCrudController<Statistic, StatisticForm>{

	public static final String FORM_TEMPLATE = "statistics/form";

	public static final String BASE_PATH = "/statistics";

	@Autowired
	private StatisticService statisticService;
	
	public StatisticController() {
		super(null, FORM_TEMPLATE, null, BASE_PATH, Statistic.class);
	}
	
	@Override
	protected StatisticForm initForm() {
		StatisticForm form = new StatisticForm();
		statisticService.fillFormFromModel(form, new Statistic());
		return form;
	}
	
}
