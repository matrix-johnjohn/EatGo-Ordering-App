package org.eatgo.common.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishTagVo {
    private Integer id;
    private String name;
    private Integer categorizeId;
    private String CateName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
