package berkeley.creations.expenses.service;

import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.model.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Expense> queryExpenses(Query query) {
        return expenseService.findAllOrdered()
                .stream()
                .filter(categoriesMatch(query))
                .filter(monthsMatch(query))
                .filter(yearMatch(query))
                .collect(Collectors.toList());
    }

    private Predicate<Expense> categoriesMatch(Query query) {
        return e -> query.getCategory() == null || e.getCategory().equals(query.getCategory());
    }

    private Predicate<Expense> monthsMatch(Query query) {
        return e -> query.getMonth() == null || e.getDate().getMonth() == (query.getMonth());
    }

    private Predicate<Expense> yearMatch(Query query) {
        return e -> query.getYear() == null || e.getDate().getYear() == (query.getYear().getValue());
    }

}
