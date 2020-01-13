package berkeley.creations.expenses.repository;

import berkeley.creations.expenses.model.Expense;
import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
}
