package com.example.soonsul.user.entity;

import com.example.soonsul.liquor.entity.Liquor;
import lombok.*;

import javax.persistence.*;

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

    @Column(name = "body_feel")
    private Integer bodyFeel;

    @Column(name = "scent")
    private Integer scent;

    @Column(name = "density")
    private Integer density;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="liquor_id")
    private Liquor liquor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

}
