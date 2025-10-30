package org.eatgo.common.domain.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    private Integer id;
    private Integer userId;
    private Integer dishId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
