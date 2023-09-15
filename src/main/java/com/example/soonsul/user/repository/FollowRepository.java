package com.example.soonsul.user.repository;

import com.example.soonsul.user.entity.Follow;
import com.example.soonsul.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
    void deleteByFollowerAndFollowing(User follower, User following);
    List<Follow> findAllByFollower(User follower);
    List<Follow> findAllByFollowing(User following);
}
