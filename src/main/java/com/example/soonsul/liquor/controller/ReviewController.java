package com.example.soonsul.liquor.controller;

import com.example.soonsul.liquor.dto.CommentDto;
import com.example.soonsul.liquor.dto.ReviewDto;
import com.example.soonsul.liquor.response.CommentListResponse;
import com.example.soonsul.liquor.response.ReviewListResponse;
import com.example.soonsul.liquor.response.ReviewResponse;
import com.example.soonsul.liquor.service.CommentService;
import com.example.soonsul.liquor.service.ReviewService;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="Review")
@RestController
@RequiredArgsConstructor
@RequestMapping("/liquor")
public class ReviewController {
    private final ReviewService reviewService;
    private final CommentService commentService;


    @ApiOperation(value = "전통주 리뷰 전체 조회 - 최신순")
    @GetMapping("/{liquorId}/review/latest")
    public ResponseEntity<ReviewListResponse> getReviewListByLatest(@PageableDefault(size=10, sort = "review_id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("liquorId") String liquorId) {
        final List<ReviewDto> data= reviewService.getReviewListByLatest(pageable, liquorId);
        return ResponseEntity.ok(ReviewListResponse.of(ResultCode.GET_REVIEW_LIST_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 리뷰 전체 조회 - 평점순")
    @GetMapping("/{liquorId}/review/rating")
    public ResponseEntity<ReviewListResponse> getReviewListByRating(@PageableDefault(size=10, sort = "review_id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("liquorId") String liquorId) {
        final List<ReviewDto> data= reviewService.getReviewListByRating(pageable, liquorId);
        return ResponseEntity.ok(ReviewListResponse.of(ResultCode.GET_REVIEW_LIST_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 리뷰 한개 조회")
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable("reviewId") Long reviewId) {
        final ReviewDto data= reviewService.getReview(reviewId);
        return ResponseEntity.ok(ReviewResponse.of(ResultCode.GET_REVIEW_SUCCESS, data));
    }


    @ApiOperation(value = "해당하는 리뷰의 댓글 전체 조회 - 최신순")
    @GetMapping("/review/{reviewId}/comment")
    public ResponseEntity<CommentListResponse> getCommentList(@PageableDefault(size=10, sort = "comment_id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("reviewId") Long reviewId) {
        final List<CommentDto> data= commentService.getCommentList(pageable, reviewId);
        return ResponseEntity.ok(CommentListResponse.of(ResultCode.GET_COMMENT_LIST_SUCCESS, data));
    }


    @ApiOperation(value = "리뷰 좋아요 추가")
    @PostMapping("/review/{reviewId}/like")
    public ResponseEntity<ResultResponse> postReviewLike(@PathVariable("reviewId") Long reviewId) {
        reviewService.postReviewLike(reviewId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_REVIEW_LIKE_SUCCESS));
    }


    @ApiOperation(value = "리뷰 좋아요 삭제")
    @DeleteMapping("/review/{reviewId}/like")
    public ResponseEntity<ResultResponse> deleteReviewLike(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReviewLike(reviewId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_REVIEW_LIKE_SUCCESS));
    }


    @ApiOperation(value = "해당 리뷰의 좋아요 개수 조회")
    @GetMapping("/review/{reviewId}/like")
    public ResponseEntity<ResultResponse> getReviewLike(@PathVariable("reviewId") Long reviewId) {
        final Integer data= reviewService.getReviewLike(reviewId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_REVIEW_LIKE_SUCCESS, data));
    }
}
