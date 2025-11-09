package org.eatgo.common.domain.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageQuery {
    private Integer from;
    private Integer to;
    private String message;
}
