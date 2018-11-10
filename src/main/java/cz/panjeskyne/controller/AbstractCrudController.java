package cz.panjeskyne.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.panjeskyne.form.Form;
import cz.panjeskyne.form.PaginatedResult;
import cz.panjeskyne.model.AbstractEntity;
import cz.panjeskyne.service.BaseService;

public abstract class AbstractCrudController<M extends AbstractEntity, F extends Form> {

	private static final String ID_ATTR = "id";

	private static final String FORM_ATTR = "form";

	@Autowired
	private BaseService<M, F> service;

	private String displayTemplate;

	private String formTemplate;

	private String listTemplate;

	private Class<M> modelClass;

	private String basePath;
	
	private int defaultLimit = 50;

	public AbstractCrudController(String displayTemplate, String formTemplate, String listTemplate, String basePath, Class<M> modelClass) {
		this.displayTemplate = displayTemplate;
		this.formTemplate = formTemplate;
		this.basePath = basePath;
		this.modelClass = modelClass;
		this.listTemplate = listTemplate;
	}

	@ModelAttribute(name = FORM_ATTR)
	protected abstract F initForm();
	
	@RequestMapping("/{id}")
	public String main(Model model, @PathVariable(name = ID_ATTR, required = true) long id) {
		model.addAttribute("model", service.find(id));
		return displayTemplate;
	}

	@RequestMapping("/create")
	public String create(Model model, @ModelAttribute(name = FORM_ATTR) F form,
			@PathVariable(name = ID_ATTR, required = false) Long id) {
		return formTemplate;
	}

	@RequestMapping("/update/{id}")
	public String update(Model model, @ModelAttribute(name = FORM_ATTR) F form,
			@PathVariable(name = ID_ATTR, required = true) long id) {
		M entity = service.find(id);
		service.fillFormFromModel(form, entity);
		return formTemplate;
	}

	@RequestMapping("/delete/{id}")
	public String delete(Model model, @PathVariable(name = ID_ATTR, required = true) long id,
			@RequestParam(name = "returnURL", required = false) String returnURL) {
		M entity = service.find(id);
		service.delete(entity);
		return "redirect:" + (StringUtils.isBlank(returnURL) ? "/" : returnURL);
	}

	@RequestMapping("/save")
	public String save(Model model, @ModelAttribute(name = FORM_ATTR) F form) {
		return save(model, form, null);
	}

	@RequestMapping("/save/{id}")
	public String save(Model model, @ModelAttribute(name = FORM_ATTR) F form,
			@PathVariable(name = ID_ATTR, required = false) Long id) {
		M entity = null;
		if (id == null) {
			try {
				entity = modelClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO LOG
			}
		} else {
			entity = service.find(id);
		}
		service.fillModelFromForm(form, entity);
		entity = service.save(entity);
		String path = StringUtils.prependIfMissing(basePath, "/");
		path = StringUtils.appendIfMissing(path, "/");
		return "redirect:" + path + entity.getId();
	}

	@RequestMapping("/list")
	public String list(Model model) {
		return list(model, 1, defaultLimit);
	}
	
	@RequestMapping("/list/{page}/{limit}")
	public String list(Model model, @PathVariable(name = "page", required = true) int page,
			@PathVariable(name = "limit", required = true) int limit) {
		PaginatedResult<M> result = service.getAllPaginated(page, limit);
		model.addAttribute("result", result);
		return listTemplate;
	}

	public int getDefaultLimit() {
		return defaultLimit;
	}

	public void setDefaultLimit(int defaultLimit) {
		this.defaultLimit = defaultLimit;
	}
}
