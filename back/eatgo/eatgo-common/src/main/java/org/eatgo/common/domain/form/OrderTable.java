package org.eatgo.common.domain.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTable {
    private Integer id;
    private Integer userId;
    private String username;//outlet

    private Integer dishId;
    private String dishName;//outlet
    private String dishImg;//outlet
    private String dishDesc;//outlet

    private Double price;
    private Integer count;
    private Double totalPrice;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
