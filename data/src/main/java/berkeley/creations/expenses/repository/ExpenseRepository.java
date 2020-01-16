package berkeley.creations.expenses.repository;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.Expense;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    Set<Expense> findByCategory(Category category);

}
