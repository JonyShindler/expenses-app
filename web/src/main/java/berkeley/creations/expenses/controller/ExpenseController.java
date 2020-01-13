package berkeley.creations.expenses.controller;

import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@RequestMapping("/expenses")
@Controller
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @GetMapping("")
    public String showAllExpenses(Model model) {
        Set<Expense> expenses = expenseService.findAll();
        model.addAttribute("expenses", expenses);
        return "expense/allExpenses";
    }

}
