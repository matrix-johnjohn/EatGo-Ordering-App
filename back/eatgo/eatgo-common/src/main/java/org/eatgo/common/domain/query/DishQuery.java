package org.eatgo.common.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishQuery {
    private Integer categorizeId;
    private Integer tagId;
}
