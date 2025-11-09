package org.eatgo.common.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order{
    private Integer id;
    private Integer userId;
    private Integer dishId;
    private Integer price;
    private Integer count;
    private Integer totalPrice;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
