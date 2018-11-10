package cz.panjeskyne.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.panjeskyne.form.CharacterForm;
import cz.panjeskyne.model.Character;
import cz.panjeskyne.service.CharacterService;

@Controller
@RequestMapping(CharacterController.BASE_PATH)
public class CharacterController extends AbstractCrudController<Character, CharacterForm>{

	public static final String DISPLAY_TEMPLATE = "character/display";

	public static final String FORM_TEMPLATE = "character/form";

	public static final String LIST_TEMPLATE = "character/list";
	
	public static final String BASE_PATH = "/character";

	@Autowired
	private CharacterService characterService;
	
	public CharacterController() {
		super(DISPLAY_TEMPLATE, FORM_TEMPLATE, LIST_TEMPLATE, BASE_PATH, Character.class);
	}
	
	@Override
	protected CharacterForm initForm() {
		CharacterForm form = new CharacterForm();
		characterService.fillFormFromModel(form, new Character());
		return form;
	}
}
