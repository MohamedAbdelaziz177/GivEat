package com.abdelaziz26.GivEat.DTOs.Matching;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MatchingResponse {
    private Long requestId;
    private Long itemId;
    private double score;
}
