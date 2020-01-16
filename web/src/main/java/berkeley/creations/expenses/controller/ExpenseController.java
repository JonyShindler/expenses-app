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
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping()
@Controller
public class ExpenseController {

    private static final String EXPENSES_CREATE_OR_UPDATE_EXPENSE_FORM = "expenses/createOrUpdateExpenseForm";
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

    @InitBinder("/expenses/expense")
    public void initOwnerBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @GetMapping("/expenses/new")
    public String initCreationForm(Model model) {
        Expense expense = new Expense();
        expense.setDate(new Date());
        model.addAttribute("expense", expense);
        return EXPENSES_CREATE_OR_UPDATE_EXPENSE_FORM;
    }

    @PostMapping("/expenses/new")
    public String processCreationForm(@Valid Expense expense, BindingResult result, ModelMap model) {
        //TODO check it is not duplicate
        //result.rejectValue("name", "duplicate", "already exists");

        if (result.hasErrors()) {
            model.put("expense", expense);
            return EXPENSES_CREATE_OR_UPDATE_EXPENSE_FORM;
        } else {
            expenseService.save(expense);
            return "redirect:/expenses/" + expense.getId();
        }
    }

    @ModelAttribute("categories")
    public List<Category> populateCategories() {
        return categoryService.findAll().stream().sorted(Comparator.comparing(Category::getName)).collect(Collectors.toList());
    }


    @GetMapping("/expenses/{expenseId}/edit")
    public String initUpdateOwnerForm(@PathVariable Long expenseId, Model model) {
        model.addAttribute(expenseService.findById(expenseId));
        return EXPENSES_CREATE_OR_UPDATE_EXPENSE_FORM;
    }

    @PostMapping("/expenses/{expenseId}/edit")
    public String processUpdateOwnerForm(@Valid Expense expense, BindingResult result, @PathVariable Long expenseId) {
        if (result.hasErrors()) {
            return EXPENSES_CREATE_OR_UPDATE_EXPENSE_FORM;
        } else {
            expense.setId(expenseId);
            Expense savedExpense = expenseService.save(expense);
            return "redirect:/expenses/" + savedExpense.getId();
        }
    }
}
