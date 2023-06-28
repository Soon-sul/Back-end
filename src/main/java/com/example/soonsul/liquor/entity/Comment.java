package com.example.soonsul.liquor.entity;

import com.example.soonsul.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, unique = true)
    private Long commentId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "good")
    private Integer good;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "upper_comment_id")
    private Long upperCommentId;


    public void updateContent(String content){
        this.content= content;
    }

    public void updateUpperCommentId(Long upperCommentId){
        this.upperCommentId= upperCommentId;
    }

    public void addGood(Integer number) {this.good+= number;}
}
