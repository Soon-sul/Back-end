package com.example.soonsul.user.entity;

import com.example.soonsul.liquor.entity.FlavorType;
import com.example.soonsul.liquor.entity.Liquor;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="personal_evaluation")
public class PersonalEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_evaluation_id")
    private Long personalEvaluationId;

    @Column(name = "liquor_personal_rating")
    private Double liquorPersonalRating;

    @Column(name = "sweetness")
    private Integer sweetness;

    @Column(name = "acidity")
    private Integer acidity;

    @Column(name = "carbonic_acid")
    private Integer carbonicAcid;

    @Column(name = "heavy")
    private Integer heavy;

    @Column(name = "scent")
    private Integer scent;

    @Column(name = "density")
    private Integer density;

    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="liquor_id")
    private Liquor liquor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


    public void updateLiquorPersonalRating(Double liquorPersonalRating){
        this.liquorPersonalRating= liquorPersonalRating;
    }

    public void updateFlavor(FlavorType flavorType, Integer value){
        switch (flavorType){
            case SWEETNESS:
                this.sweetness= value;
                break;
            case ACIDITY:
                this.acidity= value;
                break;
            case CARBONIC_ACID:
                this.carbonicAcid= value;
                break;
            case HEAVY:
                this.heavy= value;
                break;
            case SCENT:
                this.scent= value;
                break;
            case DENSITY:
                this.density= value;
                break;
        }
    }


    public Integer getFlavor(FlavorType flavorType){
        switch (flavorType){
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
