package berkeley.creations.expenses.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="category")
@Entity
public class Category extends BaseEntity{

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
