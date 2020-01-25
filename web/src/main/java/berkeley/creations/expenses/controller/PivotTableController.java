package berkeley.creations.expenses.controller;

import berkeley.creations.expenses.model.Category;
import berkeley.creations.expenses.model.PivotRow;
import berkeley.creations.expenses.model.PivotTable;
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

    public PivotTableController(PivotTableService pivotTableService) {
        this.pivotTableService = pivotTableService;
    }

    //TODO this should ideally take a set of expenses to use?
    @GetMapping("/expenses/table")
    public String showBreakdown(Model model) {
        PivotTable table = pivotTableService.buildTable();
        model.addAttribute("table", table);
        return "expenses/pivotTable";
    }

}
