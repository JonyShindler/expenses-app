package berkeley.creations.expenses.service;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.Direction;
import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.model.PivotRow;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PivotTableService {

    private final ExpenseService expenseService;

    public PivotTableService(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public List<PivotRow> buildTable() {

        // Group all expenses by YearMonth
        Map<YearMonth, List<Expense>> expensesGroupedByMonth = getExpensesGroupedByMonth();

        // Get the total for eac YearMonth
        return expensesGroupedByMonth.entrySet().stream()
                .map(e ->PivotRow.builder()
                        .date(e.getKey())
                        .categoryTotals(sumExpensesByCategoryForMonth(e.getValue()))
                        .total(sumExpenses(e.getValue()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    private BigDecimal sumExpenses(List<Expense> expenses) {
        return expenses.stream().map(Expense::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<Category, BigDecimal> sumExpensesByCategoryForMonth(List<Expense> expensesForMonth) {
        return expensesForMonth.stream()
                .collect(Collectors.groupingBy(Expense::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Expense::getQuantity, BigDecimal::add)
                ));
    }

    private Map<YearMonth, List<Expense>> getExpensesGroupedByMonth(){
    List<Expense> allExpenses = expenseService.findAllOrdered();

    return allExpenses.stream()
            .collect(Collectors.groupingBy(exp -> YearMonth.from(exp.getDate()),
                                                                TreeMap::new,
                                            Collectors.toCollection(ArrayList::new))
    );

}

}
