package org.eatgo.common.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishVo {
    private Integer id;
    private String title;
    private String description;
    private String image;
    private Double price;
    private Integer categorizeId;
    private Integer tagId;
    private String cateName;
    private String tagName;
    private Integer collectionCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
