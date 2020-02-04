package berkeley.creations.expenses.formatter;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.service.CategoryService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class CategoryFormatter implements Formatter<Category> {
    private final CategoryService categoryService;

    public CategoryFormatter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public String print(Category category, Locale locale) {
        return category.getName();
    }

    @Override
    public Category parse(String text, Locale locale) throws ParseException {
        Collection<Category> allCategories = categoryService.findAll();

        for (Category category : allCategories) {
            if (category.getName().equals(text)) {
                return category;
            }
        }

        throw new ParseException("category not found: " + text, 0);
    }
}
