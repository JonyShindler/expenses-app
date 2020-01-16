package berkeley.creations.expenses.service;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.Expense;

import java.util.Set;

public interface ExpenseService extends CrudService<Expense, Long> {

    Set<Expense> findByCategory(Category category);

}
