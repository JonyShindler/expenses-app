package berkeley.creations.expenses.controller;

import berkeley.creations.expenses.model.Expense;
import berkeley.creations.expenses.model.PivotRow;
import berkeley.creations.expenses.service.PivotTableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@RequestMapping()
@Controller
public class PivotTableController {

    private final PivotTableService pivotTableService;

    public PivotTableController(PivotTableService pivotTableService) {
        this.pivotTableService = pivotTableService;
    }

    @GetMapping("/expenses/table")
    public String showBreakdown(Model model) {
        List<PivotRow> pivotRows = pivotTableService.buildTable();
        model.addAttribute("rows", pivotRows);
        return "/expenses/pivotTable";
    }

}
