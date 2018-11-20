package cz.panjeskyne.service;

import javax.transaction.Transactional;

import cz.panjeskyne.form.Form;
import cz.panjeskyne.model.db.AbstractEntity;

@Transactional
public interface BaseFormService<T extends AbstractEntity, F extends Form> extends BaseService<T> {

	void fillModelFromForm(F form, T model);

	void fillFormFromModel(F form, T model);

}