package berkeley.creations.expenses.controller;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.model.Query;
import berkeley.creations.expenses.service.CategoryService;
import berkeley.creations.expenses.service.ExpenseService;
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

    private final ExpenseService expenseService;
    private final CategoryService categoryService;


    public QueryController(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("categories")
    public List<Category> populateCategories() {
        return categoryService.findAll().stream().sorted(Comparator.comparing(Category::getName)).collect(Collectors.toList());
    }

    @GetMapping("/expenses/query")
    public String initQuery(Model model) {
        model.addAttribute("query", new Query());
        return "expenses/queryExpenses";
    }

    @PostMapping("/expenses/query")
    public String processQuery(Query query, Model model) {
//        model.addAttribute("query", new Query());
        Set<Expense> expenses = expenseService.findByCategory(query.getCategory());
        model.addAttribute("expenses", expenses);
        return "/expenses/queryExpenses";
    }
    //TODO we need queries to run as path variables so we can do it for API etc...

}
