package com.example.soonsul.liquor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EvaluationRequest {
    private Double liquorPersonalRating;
    private Integer sweetness;
    private Integer acidity;
    private Integer carbonicAcid;
    private Integer heavy;
    private Integer scent;
    private Integer density;
    private String reviewContent;
}
