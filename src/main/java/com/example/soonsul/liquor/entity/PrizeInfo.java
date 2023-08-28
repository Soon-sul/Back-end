package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="prize_info")
public class PrizeInfo {

    @Id
    @Column(name = "prize_info_id", nullable = false, unique = true)
    private String prizeInfoId;

    @Column(name = "name")
    private String name;
}
