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

    private Integer price;
    private Integer count;
    private Integer totalPrice;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
