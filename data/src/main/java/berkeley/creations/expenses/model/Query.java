package berkeley.creations.expenses.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Query {

    //TODO we need to decide how to handle the months stuff. probably just do greater and less than? not sure..

    private Category category;

}