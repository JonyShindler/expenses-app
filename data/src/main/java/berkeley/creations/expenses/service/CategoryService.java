package berkeley.creations.expenses.service;

import berkeley.creations.expenses.model.Category;

public interface CategoryService extends CrudService<Category, Long> {

    Category findByName(String name);

}
