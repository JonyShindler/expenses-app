package berkeley.creations.expenses.service;

import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.model.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class QueryService {

    private final ExpenseService expenseService;

    @Autowired
    public QueryService(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public Set<Expense> queryExpenses(Query query) {
        return expenseService.findAll()
                .stream()
                .filter(categoriesMatch(query))
                .filter(monthsMatch(query))
                .collect(Collectors.toSet());

    }

    private Predicate<Expense> categoriesMatch(Query query) {
        return e -> e.getCategory().equals(query.getCategory());
    }

    private Predicate<Expense> monthsMatch(Query query) {
        return e -> e.getDate().getMonth() == (query.getMonth());
    }

}
