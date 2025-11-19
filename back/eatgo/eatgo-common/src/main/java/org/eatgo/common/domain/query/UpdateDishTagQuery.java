package org.eatgo.common.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishTagQuery {
    private Integer id;
    private String name;
    private Integer cateId;
}
