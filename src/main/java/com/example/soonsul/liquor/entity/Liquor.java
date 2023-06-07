package com.example.soonsul.liquor.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="liquor")
public class Liquor {

    @Id
    @Column(name = "liquor_id", nullable = false, unique = true)
    private String liquorId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    @JoinColumn(name="liquor_info_id")
    private LiquorInfo liquorInfo;
}
