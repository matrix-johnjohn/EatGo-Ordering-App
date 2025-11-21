package org.eatgo.common.domain.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishSearchForm {
    private String title;
    private Integer dishCateId;
    private Integer dishTagId;
}
