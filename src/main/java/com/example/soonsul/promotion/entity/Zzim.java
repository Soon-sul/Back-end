package com.example.soonsul.promotion.entity;

import com.example.soonsul.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="zzim")
public class Zzim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zzim_id", nullable = false, unique = true)
    private Long zzimId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="promotion_id")
    private Promotion promotion;
}
