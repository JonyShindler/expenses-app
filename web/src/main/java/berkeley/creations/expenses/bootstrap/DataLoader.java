package berkeley.creations.expenses.bootstrap;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.Direction;
import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.service.CategoryService;
import berkeley.creations.expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    @Autowired
    public DataLoader(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        Category salaryCategory = categoryService.save(Category.builder().name("Salary").build());
        Category lunchCategory = categoryService.save(Category.builder().name("Lunch").build());
        Category bigPurchasesCategory = categoryService.save(Category.builder().name("Big Purchases").build());
        Category holidayCategory = categoryService.save(Category.builder().name("Holiday").build());
        Category trainCategory = categoryService.save(Category.builder().name("Train Pass").build());

        expenseService.save(Expense.builder()
                .date(new Date()).detail("Panda food").quantity(new BigDecimal("23.45"))
                .direction(Direction.OUT).category(lunchCategory).build());

        expenseService.save(Expense.builder()
                .date(new Date()).detail("Lunch - nandos").quantity(new BigDecimal("12.00"))
                .direction(Direction.OUT).category(lunchCategory).build());

        expenseService.save(Expense.builder()
                .date(new Date()).detail("monaaaaay").quantity(new BigDecimal("1000.00"))
                .direction(Direction.IN).category(salaryCategory).build());

        System.out.println("Loading expenses");
    }
}
