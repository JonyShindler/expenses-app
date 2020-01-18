package berkeley.creations.expenses.bootstrap;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.Direction;
import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.service.CategoryService;
import berkeley.creations.expenses.service.ExpenseService;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
//        buildSampleData();
        load2016Data();
    }

    private void load2016Data() throws IOException {
        System.out.println("Loading expenses");
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader("C:\\Users\\jonathan\\IdeaProjects\\expenses-app\\web\\src\\main\\resources\\sampleData\\2016Data.csv"));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                // 0 = month
                // 1 = year
                // 2 = quantity
                // 3 = categoryName
                // 4 = details
                String date = values[0];
                BigDecimal quantity = bd(values[2]);
                Direction direction = OUT;
                if (quantity.compareTo(BigDecimal.ZERO) < 0) {
                    direction = IN;
                }

                String categoryName = values[3];
                String detail = values[4];

                if (categoryService.findByName(categoryName) == null) {
                    categoryService.save(Category.builder().name(categoryName).build());
                }
                Category category = categoryService.findByName(categoryName);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(date.replaceAll("[\uFEFF-\uFFFF]", ""), formatter);

                expenseService.save(Expense.builder()
                        .quantity(quantity)
                        .category(category)
                        .date(localDate)
                        .direction(direction)
                        .detail(detail)
                        .build());
                records.add(Arrays.asList(values));
            }
        }

        System.out.println("CSV records: " + records.size());
    }

    private void buildSampleData() {
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
    }

    private BigDecimal bd(String s) {
        return new BigDecimal(s);
    }
}
