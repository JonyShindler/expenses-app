package berkeley.creations.expenses.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="expense")
@Entity
public class Expense extends BaseEntity{

    private BigDecimal quantity;
    private Date date;
    private Direction direction;
//    private Category category;
    private String detail;

}
