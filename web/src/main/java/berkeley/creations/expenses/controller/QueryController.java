package berkeley.creations.expenses.controller;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.model.Query;
import berkeley.creations.expenses.service.CategoryService;
import berkeley.creations.expenses.service.ExpenseService;
import berkeley.creations.expenses.service.QueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping()
@Controller
public class QueryController {

    private final QueryService queryService;
    private final CategoryService categoryService;
    private final ExpenseService expenseService;

    public QueryController(QueryService queryService, CategoryService categoryService, ExpenseService expenseService) {
        this.queryService = queryService;
        this.categoryService = categoryService;
        this.expenseService = expenseService;
    }

    @ModelAttribute("categories")
    public List<Category> populateCategories() {
        return categoryService.findAll().stream().sorted(Comparator.comparing(Category::getName)).collect(Collectors.toList());
    }

    @ModelAttribute("years")
    public List<Integer> populateYears() {
        return expenseService.findAll().stream().map(e -> e.getDate().getYear()).distinct().sorted().collect(Collectors.toList());
    }

    @GetMapping("/expenses/query")
    public String initQuery(Model model) {
        model.addAttribute("expenses", expenseService.findAllOrdered());
        model.addAttribute("query", new Query());
        return "expenses/queryExpenses";
    }

    @PostMapping("/expenses/query")
    public String processQuery(Query query, Model model) {
//        model.addAttribute("query", new Query());
        List<Expense> expenses = queryService.queryExpenses(query);
        model.addAttribute("expenses", expenses);
        return "expenses/queryExpenses";
    }
    //TODO we need queries to run as path variables so we can do it for API etc...

}
