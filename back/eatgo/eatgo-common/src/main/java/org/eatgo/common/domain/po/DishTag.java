package org.eatgo.common.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishTag {
    private Integer id;
    private String name;
    private Integer categorizeId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
