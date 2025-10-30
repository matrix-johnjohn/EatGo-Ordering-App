package org.eatgo.common.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish{
    private Integer id;
    private String title;
    private String description;
    private String image;
    private Double price;
    private Integer categorizeId;
    private Integer tagId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
