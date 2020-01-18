package berkeley.creations.expenses.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate date;

    private Direction direction;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String detail;


    @Override
    public String toString() {
        return "Expense{" +
                "quantity=" + quantity +
                ", date=" + date +
                ", direction=" + direction +
                ", category=" + category +
                ", detail='" + detail + '\'' +
                '}';
    }
}
