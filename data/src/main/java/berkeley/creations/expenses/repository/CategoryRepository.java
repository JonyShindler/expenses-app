package berkeley.creations.expenses.repository;

import berkeley.creations.expenses.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
