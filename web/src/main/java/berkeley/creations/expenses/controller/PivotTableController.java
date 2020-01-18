package berkeley.creations.expenses.controller;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.PivotRow;
import berkeley.creations.expenses.service.CategoryService;
import berkeley.creations.expenses.service.PivotTableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping()
@Controller
public class PivotTableController {

    private final PivotTableService pivotTableService;
    private final CategoryService categoryService;

    public PivotTableController(PivotTableService pivotTableService, CategoryService categoryService) {
        this.pivotTableService = pivotTableService;
        this.categoryService = categoryService;
    }

    @GetMapping("/expenses/table")
    public String showBreakdown(Model model) {
        List<PivotRow> pivotRows = pivotTableService.buildTable();
        model.addAttribute("rows", pivotRows);
        return "/expenses/pivotTable";
    }

    @ModelAttribute("categories")
    public List<Category> populateCategories() {
        return categoryService.findAll().stream().sorted(Comparator.comparing(Category::getName)).collect(Collectors.toList());
    }

}
