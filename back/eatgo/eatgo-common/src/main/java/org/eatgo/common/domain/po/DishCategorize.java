package org.eatgo.common.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishCategorize {
    private Integer id;
    private String name;
    private String icon;
    private String banner;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
