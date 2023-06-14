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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id", nullable = false, unique = true)
    private String evaluationId;

    @Column(name = "sweetness")
    private Integer sweetness;

    @Column(name = "acidity")
    private Integer acidity;

    @Column(name = "carbonic_acid")
    private Integer carbonicAcid;

    @Column(name = "body_feel")
    private Integer bodyFeel;

    @Column(name = "scent")
    private Integer scent;

    @Column(name = "density")
    private Integer density;

}
