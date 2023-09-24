package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="prize")
public class Prize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prize_id", nullable = false, unique = true)
    private Long prizeId;

    @Column(name = "prize_info_id")
    private Long prizeInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="liquor_id")
    private Liquor liquor;
}
