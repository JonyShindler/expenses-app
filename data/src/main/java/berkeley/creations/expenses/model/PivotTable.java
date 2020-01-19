package berkeley.creations.expenses.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PivotTable {

    List<Category> categories;
    List<PivotRow> rows;
    PivotRow totalsRow;

}
