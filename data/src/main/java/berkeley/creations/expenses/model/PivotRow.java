package berkeley.creations.expenses.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PivotRow {

    //Object to represent each row of the pivot table.
    //They are keyed by month.

    private YearMonth date;
    private BigDecimal total;
    private Map<Category, BigDecimal> categoryTotals;

    @Override
    public String toString() {
        return "PivotRow{" +
                "date=" + date +
                ", total=" + total +
                '}';
    }
}
