package org.eatgo.common.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserComment {
    private Integer id;
    private String comment;
    private Integer userId;
    private Integer dishId;
    private String avatar;
    private String username;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
