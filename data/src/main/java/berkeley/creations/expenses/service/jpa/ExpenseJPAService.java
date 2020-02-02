package berkeley.creations.expenses.service.jpa;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.repository.ExpenseRepository;
import berkeley.creations.expenses.service.ExpenseService;
import berkeley.creations.expenses.service.PivotTableService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ExpenseJPAService implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseJPAService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Set<Expense> findAll() {
        Set<Expense> expenses = new HashSet<>();
        expenseRepository.findAll().forEach(expenses::add);
        return expenses;
    }

    @Override
    public Expense findById(Long Id) {
        return expenseRepository.findById(Id).orElse(null);
    }

    @Override
    public Expense save(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public void delete(Expense expense) {
        expenseRepository.delete(expense);
    }

    @Override
    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public Set<Expense> findByCategory(Category category) {
        return expenseRepository.findByCategory(category);
    }

    public List<Expense> findAllOrdered() {
        return StreamSupport.stream(expenseRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(Expense::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public Object[][] getCategoryTotalsPerMonth() {

        //TODO really this should just be only positive expensses, then we cna have a seperate one for inbound money?
        //TODO also would be nice to sort the values largest to smallest.

        //This needs to return an array of Category to total
        Map<Category, BigDecimal> categoryTotalsMap = PivotTableService.sumExpensesByCategoryForMonth(findAllOrdered());
        categoryTotalsMap.entrySet().removeIf((e -> e.getValue().compareTo(BigDecimal.ZERO)<0));

        int numberOfKeys = categoryTotalsMap.keySet().size();
        Object[][] pieData = new Object[numberOfKeys+1][2];

        pieData[0][0] = "Category";
        pieData[0][1] = "Total spent";

        int i = 1;
        for (Map.Entry<Category, BigDecimal> entry : categoryTotalsMap.entrySet()) {
            pieData[i][0] = entry.getKey().getName();
            pieData[i][1] = entry.getValue();
            i ++;
        }

        return pieData;
    }

}
