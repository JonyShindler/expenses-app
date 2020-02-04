package berkeley.creations.expenses.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryTotal {

    private final Category category;
    private final BigDecimal total;

}
