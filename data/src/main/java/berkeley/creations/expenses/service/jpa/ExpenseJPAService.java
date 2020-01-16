package berkeley.creations.expenses.service.jpa;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.repository.ExpenseRepository;
import berkeley.creations.expenses.service.ExpenseService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
}
