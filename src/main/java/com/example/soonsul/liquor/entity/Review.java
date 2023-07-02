package com.example.soonsul.liquor.entity;

import com.example.soonsul.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false, unique = true)
    private Long reviewId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "good")
    private Integer good;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "liquor_rating", nullable = false)
    private Double liquorRating;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="liquor_id")
    private Liquor liquor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public void updateContent(String content){
        this.content= content;
    }

    public void updateLiquorRating(Double liquorRating){
        this.liquorRating= liquorRating;
    }

    public void addGood(Integer number) {this.good+= number;}
}