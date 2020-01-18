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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static berkeley.creations.expenses.model.Direction.*;
import static java.time.LocalDate.*;

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
                .date(now().plusMonths(3)).detail("Panda food").quantity(bd("23.45")).direction(OUT).category(lunchCategory).build());

        expenseService.save(Expense.builder()
                .date(now()).detail("Lunch - nandos").quantity(bd("12.00")).direction(OUT).category(lunchCategory).build());

        expenseService.save(Expense.builder()
                .date(now()).detail("monaaaaay").quantity(bd("1000.00")).direction(IN).category(salaryCategory).build());

        expenseService.save(Expense.builder()
                .date(now().minusYears(1)).detail("Norway").quantity(bd("1507.40")).direction(OUT).category(holidayCategory).build());

        System.out.println("Loading expenses");
    }

    private BigDecimal bd(String s) {
        return new BigDecimal(s);
    }
}
