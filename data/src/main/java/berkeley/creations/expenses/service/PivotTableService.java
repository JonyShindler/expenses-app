package berkeley.creations.expenses.service;

import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.model.PivotRow;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

        // Get the total for each.
        return expensesGroupedByMonth.entrySet().stream()
                .map(e ->PivotRow.builder()
                        .date(e.getKey())
                        .total(sumExpenses(e.getValue()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    private BigDecimal sumExpenses(List<Expense> expenses) {
        return expenses.stream().map(Expense::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<YearMonth, List<Expense>> getExpensesGroupedByMonth(){
        Set<Expense> allExpenses = expenseService.findAll();

        return allExpenses.stream()
                .collect(Collectors.groupingBy(exp -> YearMonth.from(exp.getDate()),
                                                                    TreeMap::new,
                                                Collectors.toCollection(ArrayList::new))
        );

    }

}
