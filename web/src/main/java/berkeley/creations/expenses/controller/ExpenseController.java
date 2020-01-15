package berkeley.creations.expenses.controller;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.service.CategoryService;
import berkeley.creations.expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RequestMapping()
@Controller
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }


    @GetMapping({"/expenses","/",""})
    public String showAllExpenses(Model model) {
        Set<Expense> expenses = expenseService.findAll();
        model.addAttribute("expenses", expenses);
        return "expenses/allExpenses";
    }

    @GetMapping("/expenses/{expenseId}")
    public ModelAndView showExpense(@PathVariable("expenseId") Long expenseId) {
        ModelAndView mav = new ModelAndView("/expenses/expenseDetails");
        mav.addObject(expenseService.findById(expenseId));
        return mav;
    }


    @GetMapping("/expenses/new")
    public String initCreationForm(Model model) {
        Expense expense = new Expense();
        model.addAttribute("expense", expense);
        return "expenses/createOrUpdateExpenseForm";
    }

    @InitBinder("/expenses/expense")
    public void initOwnerBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @PostMapping("/expenses/new")
    public String processCreationForm(@Valid Expense expense, BindingResult result, ModelMap model) {
        //TODO check it is not duplicate
        //result.rejectValue("name", "duplicate", "already exists");

        if (result.hasErrors()) {
            model.put("expense", expense);
            return "expenses/createOrUpdateExpenseForm";
        } else {
            expenseService.save(expense);
            return "redirect:/expenses/" + expense.getId();
        }
    }

    @ModelAttribute("categories")
    public Collection<Category> populateCategories() {
        return categoryService.findAll();
    }

}
