package com.example.soonsul.user.entity;

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

    public void updateSweetness(Integer sweetness){
        this.sweetness= sweetness;
    }

    public void updateAcidity(Integer acidity){
        this.acidity= acidity;
    }

    public void updateCarbonicAcid(Integer carbonicAcid){
        this.carbonicAcid= carbonicAcid;
    }

    public void updateHeavy(Integer heavy){
        this.heavy= heavy;
    }

    public void updateScent(Integer scent){
        this.scent= scent;
    }

    public void updateDensity(Integer density){
        this.density= density;
    }

}
