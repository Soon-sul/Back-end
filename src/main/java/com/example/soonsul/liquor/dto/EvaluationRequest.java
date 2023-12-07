package com.example.soonsul.liquor.dto;

import com.example.soonsul.liquor.entity.FlavorType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRequest {
    private Double liquorPersonalRating;
    private Integer sweetness;
    private Integer acidity;
    private Integer carbonicAcid;
    private Integer heavy;
    private Integer scent;
    private Integer density;
    private String reviewContent;
    private List<MultipartFile> images;


    public Integer getFlavor(FlavorType fType){
        switch (fType){
            case SWEETNESS:
                return this.sweetness;
            case ACIDITY:
                return this.acidity;
            case CARBONIC_ACID:
                return this.carbonicAcid;
            case HEAVY:
                return this.heavy;
            case SCENT:
                return this.scent;
            case DENSITY:
                return this.density;
            default:
                return 1;
        }
    }
}
