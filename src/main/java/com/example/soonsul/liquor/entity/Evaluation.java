package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="evaluation")
public class Evaluation {

    @Id
    @Column(name = "evaluation_id", nullable = false, unique = true)
    private String evaluationId;

    @Column(name = "sweetness")
    private Double sweetness;

    @Column(name = "acidity")
    private Double acidity;

    @Column(name = "carbonic_acid")
    private Double carbonicAcid;

    @Column(name = "heavy")
    private Double heavy;

    @Column(name = "scent")
    private Double scent;

    @Column(name = "density")
    private Double density;


    public void updateSweetness(Double sweetness){
        this.sweetness= sweetness;
    }

    public void updateAcidity(Double acidity){
        this.acidity= acidity;
    }

    public void updateCarbonicAcid(Double carbonicAcid){
        this.carbonicAcid= carbonicAcid;
    }

    public void updateHeavy(Double heavy){
        this.heavy= heavy;
    }

    public void updateScent(Double scent){
        this.scent= scent;
    }

    public void updateDensity(Double density){
        this.density= density;
    }
}
