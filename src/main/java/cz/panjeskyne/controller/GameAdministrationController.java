package cz.panjeskyne.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.panjeskyne.form.CharacterForm;
import cz.panjeskyne.model.db.Character;
import cz.panjeskyne.service.CharacterService;
import cz.panjeskyne.service.StatisticService;

@Controller
public class GameAdministrationController {

	public static final String FORM_TEMPLATE = "administration/main";

	public static final String BASE_PATH = "/game-administration";

	@Autowired
	private CharacterService characterService;
	
	@Autowired
	private StatisticService statisticService;
	
	protected CharacterForm initForm() {
		CharacterForm form = new CharacterForm();
		characterService.fillFormFromModel(form, new Character());
		return form;
	}
	
	@RequestMapping(GameAdministrationController.BASE_PATH)
	public String administrace(Model model) {
		model.addAttribute("statistic", statisticService.getAll());
		return FORM_TEMPLATE;
	}
}
