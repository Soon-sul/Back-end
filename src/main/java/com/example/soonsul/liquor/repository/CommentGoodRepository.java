package com.example.soonsul.liquor.repository;

import com.example.soonsul.liquor.entity.Comment;
import com.example.soonsul.liquor.entity.CommentGood;
import com.example.soonsul.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentGoodRepository extends JpaRepository<CommentGood, Long> {
    Integer countByComment(Comment comment);
    boolean existsByCommentAndUser(Comment comment, User user);
    void deleteByCommentAndUser(Comment comment, User user);
}
