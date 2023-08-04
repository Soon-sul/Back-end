package com.example.soonsul.liquor.service;

import com.example.soonsul.liquor.dto.ReviewDto;
import com.example.soonsul.liquor.entity.Liquor;
import com.example.soonsul.liquor.entity.Review;
import com.example.soonsul.liquor.entity.ReviewGood;
import com.example.soonsul.liquor.exception.LiquorNotExist;
import com.example.soonsul.liquor.exception.ReviewNotExist;
import com.example.soonsul.liquor.repository.*;
import com.example.soonsul.response.error.ErrorCode;
import com.example.soonsul.user.entity.PersonalEvaluation;
import com.example.soonsul.user.entity.User;
import com.example.soonsul.user.repository.PersonalEvaluationRepository;
import com.example.soonsul.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final LiquorRepository liquorRepository;
    private final PersonalEvaluationRepository personalEvaluationRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final UserUtil userUtil;
    private final ReviewGoodRepository reviewGoodRepository;


    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewListByLatest(Pageable pageable, String liquorId){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final List<Review> reviews= reviewRepository.findAllByLatest(pageable, liquorId).toList();

        final List<ReviewDto> result= new ArrayList<>();
        for(Review r: reviews){
            final PersonalEvaluation evaluation= personalEvaluationRepository.findByUserAndLiquor(r.getUser(), liquor)
                    .orElseThrow(()-> new LiquorNotExist("liquor evaluation not exist", ErrorCode.LIQUOR_NOT_EXIST));

            final ReviewDto reviewDto= ReviewDto.builder()
                    .reviewId(r.getReviewId())
                    .averageRating(evaluation.getLiquorPersonalRating())
                    .content(r.getContent())
                    .goodNumber(reviewGoodRepository.countByReview(r))
                    .createdDate(dateConversion(r.getCreatedDate()))
                    .commentNumber(commentRepository.countByReview(r))
                    .userId(r.getUser().getUserId())
                    .nickname(r.getUser().getNickname())
                    .profileImage(r.getUser().getProfileImage())
                    .reviewNumber(reviewRepository.countByUser(r.getUser()))
                    .flagGood(reviewGoodRepository.existsByReviewAndUser(r, user))
                    .build();
            result.add(reviewDto);
        }
        return result;
    }


    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewListByRating(Pageable pageable, String liquorId){
        final User user= userUtil.getUserByAuthentication();
        final Liquor liquor= liquorRepository.findById(liquorId)
                .orElseThrow(()-> new LiquorNotExist("liquor not exist", ErrorCode.LIQUOR_NOT_EXIST));
        final List<Review> reviews= reviewRepository.findAllByRating(pageable, liquorId).toList();

        final List<ReviewDto> result= new ArrayList<>();
        for(Review r: reviews){
            final PersonalEvaluation evaluation= personalEvaluationRepository.findByUserAndLiquor(r.getUser(), liquor)
                    .orElseThrow(()-> new LiquorNotExist("liquor evaluation not exist", ErrorCode.LIQUOR_NOT_EXIST));

            final ReviewDto reviewDto= ReviewDto.builder()
                    .reviewId(r.getReviewId())
                    .averageRating(evaluation.getLiquorPersonalRating())
                    .content(r.getContent())
                    .goodNumber(reviewGoodRepository.countByReview(r))
                    .createdDate(dateConversion(r.getCreatedDate()))
                    .commentNumber(commentRepository.countByReview(r))
                    .userId(r.getUser().getUserId())
                    .nickname(r.getUser().getNickname())
                    .profileImage(r.getUser().getProfileImage())
                    .reviewNumber(reviewRepository.countByUser(r.getUser()))
                    .flagGood(reviewGoodRepository.existsByReviewAndUser(r, user))
                    .build();
            result.add(reviewDto);
        }
        return result;
    }


    @Transactional(readOnly = true)
    public ReviewDto getReview(Long reviewId){
        final User user= userUtil.getUserByAuthentication();
        final Review review= reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotExist("review not exist", ErrorCode.REVIEW_NOT_EXIST));
        final PersonalEvaluation evaluation= personalEvaluationRepository.findByUserAndLiquor(review.getUser(), review.getLiquor())
                .orElseThrow(()-> new LiquorNotExist("liquor evaluation not exist", ErrorCode.LIQUOR_NOT_EXIST));

        return ReviewDto.builder()
                .reviewId(review.getReviewId())
                .averageRating(evaluation.getLiquorPersonalRating())
                .content(review.getContent())
                .goodNumber(reviewGoodRepository.countByReview(review))
                .createdDate(dateConversion(review.getCreatedDate()))
                .commentNumber(commentRepository.countByReview(review))
                .userId(review.getUser().getUserId())
                .nickname(review.getUser().getNickname())
                .profileImage(review.getUser().getProfileImage())
                .reviewNumber(reviewRepository.countByUser(review.getUser()))
                .flagGood(reviewGoodRepository.existsByReviewAndUser(review, user))
                .build();
    }


    @Transactional
    public void postReviewLike(Long reviewId){
        final User user= userUtil.getUserByAuthentication();
        final Review review= reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotExist("review not exist", ErrorCode.REVIEW_NOT_EXIST));

        final ReviewGood good= ReviewGood.builder()
                .review(review)
                .user(user)
                .build();
        reviewGoodRepository.save(good);
    }


    @Transactional
    public void deleteReviewLike(Long reviewId){
        final User user= userUtil.getUserByAuthentication();
        final Review review= reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotExist("review not exist", ErrorCode.REVIEW_NOT_EXIST));

        reviewGoodRepository.deleteByReviewAndUser(review, user);
    }


    @Transactional(readOnly = true)
    public Integer getReviewLike(Long reviewId){
        final Review review= reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotExist("review not exist", ErrorCode.REVIEW_NOT_EXIST));
        return reviewGoodRepository.countByReview(review);
    }



    private String dateConversion(LocalDateTime request){
        final LocalDateTime now= LocalDateTime.now();
        final long subSecond= ChronoUnit.SECONDS.between(request, now);

        if(subSecond<=60) return "지금";
        else if(subSecond<=3600){
            int min= Long.valueOf(subSecond).intValue()/60;
            return min+"분 전";
        }
        else if(subSecond<=86400){
            int min= Long.valueOf(subSecond).intValue()/3600;
            return min+"시간 전";
        }
        else if(subSecond<=604800){
            int min= Long.valueOf(subSecond).intValue()/86400;
            return min+"일 전";
        }
        else return request.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
