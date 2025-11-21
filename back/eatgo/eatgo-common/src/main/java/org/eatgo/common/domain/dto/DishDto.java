package org.eatgo.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDto {
    private String title;
    private Integer categorizeId;
    private Integer tagId;
    private String image;
    private String description;
    private Double price;
}
