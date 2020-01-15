package berkeley.creations.expenses.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Direction direction;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String detail;

}
