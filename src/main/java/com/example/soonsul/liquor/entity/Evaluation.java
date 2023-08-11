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


    public void updateFlavor(FlavorType flavorType, Double value){
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

    public Double getFlavor(FlavorType flavorType){
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
                return 1.0;
        }
    }
}
