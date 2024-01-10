package com.example.soonsul.promotion.entity;

import com.example.soonsul.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="promotion_liquor")
public class PromotionLiquor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_liquor_id", nullable = false, unique = true)
    private Long promotionLiquorId;

    @Column(name = "liquor_id")
    private String liquorId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="promotion_id")
    private Promotion promotion;
}
