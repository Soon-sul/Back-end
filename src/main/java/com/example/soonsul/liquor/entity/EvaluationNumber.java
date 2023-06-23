package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="evaluation_number")
public class EvaluationNumber {

    @Id
    @Column(name = "liquor_id", nullable = false, unique = true)
    private String liquorId;

    @Column(name = "average_rating")
    private Integer averageRating;

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


    public void addAverageRating(Integer number){
        this.averageRating+= number;
    }

    public void addSweetness(Integer number){
        this.sweetness+= number;
    }

    public void addAcidity(Integer number){
        this.acidity+= number;
    }

    public void addCarbonicAcid(Integer number){
        this.carbonicAcid+= number;
    }

    public void addHeavy(Integer number){
        this.heavy+= number;
    }

    public void addScent(Integer number){
        this.scent+= number;
    }

    public void addDensity(Integer number){
        this.density+= number;
    }
}
