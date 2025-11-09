package org.eatgo.common.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.eatgo.common.domain.po.Order;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo{
    private Integer id;
    private Integer userId;
    private Integer dishId;
    private Double price;
    private Integer count;
    private Integer status;
    private Double totalPrice;
    private String dishName;
    private String dishImg;
    private String dishDesc;
}
