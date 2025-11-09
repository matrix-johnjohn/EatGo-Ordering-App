package org.eatgo.common.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentQuery {
    private String comment;
    private Integer userId;
    private Integer dishId;
}
