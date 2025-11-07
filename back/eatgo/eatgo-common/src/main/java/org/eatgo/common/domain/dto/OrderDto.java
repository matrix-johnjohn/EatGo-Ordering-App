package org.eatgo.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto{
    private Integer userId;
    private Integer dishId;
    private Double price;
    private Integer count;
    private Double totalPrice;
}
