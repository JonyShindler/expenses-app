package berkeley.creations.expenses.service.jpa;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.repository.CategoryRepository;
import berkeley.creations.expenses.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryJPAService implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryJPAService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> findAll() {
        Set<Category> categories = new HashSet<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    public Category findById(Long Id) {
        return categoryRepository.findById(Id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
