package com.example.soonsul.liquor.controller;

import com.example.soonsul.liquor.dto.*;
import com.example.soonsul.liquor.response.*;
import com.example.soonsul.liquor.service.CommentService;
import com.example.soonsul.liquor.service.EvaluationService;
import com.example.soonsul.liquor.service.LiquorService;
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

@Api(tags="Liquor")
@RestController
@RequiredArgsConstructor
@RequestMapping("/liquor")
public class LiquorController {
    private final LiquorService liquorService;
    private final EvaluationService evaluationService;
    private final ReviewService reviewService;
    private final CommentService commentService;


    @ApiOperation(value = "전통주 상세 정보 조회")
    @GetMapping("/{liquorId}/info")
    public ResponseEntity<LiquorInfoResponse> getLiquorInfo(@PathVariable("liquorId") String liquorId) {
        final LiquorInfoDto data= liquorService.getLiquorInfo(liquorId);
        return ResponseEntity.ok(LiquorInfoResponse.of(ResultCode.GET_LIQUOR_INFO_SUCCESS, data));
    }

    @ApiOperation(value = "전통주 평가 여부 - 평점에 대한 평가 여부도 됨")
    @GetMapping("/{liquorId}/evaluation/check")
    public ResponseEntity<ResultResponse> getEvaluationCheck(@PathVariable("liquorId") String liquorId) {
        final boolean data= liquorService.getEvaluationCheck(liquorId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_EVALUATION_CHECK_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 전체 맛평가 조회")
    @GetMapping("/{liquorId}/flavor-evaluation/average")
    public ResponseEntity<EvaluationResponse> getFlavorAverage(@PathVariable("liquorId") String liquorId) {
        final EvaluationDto data= liquorService.getFlavorAverage(liquorId);
        return ResponseEntity.ok(EvaluationResponse.of(ResultCode.GET_FLAVOR_AVERAGE_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 개인 맛평가 조회")
    @GetMapping("/{liquorId}/flavor-evaluation/person")
    public ResponseEntity<PersonEvaluationResponse> getFlavorPerson(@PathVariable("liquorId") String liquorId) {
        final PersonEvaluationDto data= liquorService.getFlavorPerson(liquorId);
        return ResponseEntity.ok(PersonEvaluationResponse.of(ResultCode.GET_FLAVOR_PERSON_SUCCESS, data));
    }


    @ApiOperation(value = "전통주 맛평가 여부")
    @GetMapping("/{liquorId}/flavor-evaluation/check")
    public ResponseEntity<ResultResponse> getFlavorCheck(@PathVariable("liquorId") String liquorId) {
        final boolean data= liquorService.getFlavorCheck(liquorId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_FLAVOR_CHECK_SUCCESS, data));
    }


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

    
    @ApiOperation(value = "전통주에 대한 평가 등록")
    @PostMapping("/{liquorId}/evaluation")
    public ResponseEntity<ResultResponse> postEvaluation(@PathVariable("liquorId") String liquorId, @RequestBody EvaluationRequest request) {
        evaluationService.postEvaluation(liquorId, request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_EVALUATION_SUCCESS));
    }


    @ApiOperation(value = "전통주에 대한 평가 수정")
    @PutMapping("/{liquorId}/evaluation")
    public ResponseEntity<ResultResponse> putEvaluation(@PathVariable("liquorId") String liquorId, @RequestBody EvaluationRequest request) {
        evaluationService.putEvaluation(liquorId, request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PUT_EVALUATION_SUCCESS));
    }


    @ApiOperation(value = "댓글 작성")
    @PostMapping("/review/{reviewId}/comment")
    public ResponseEntity<ResultResponse> postComment(@PathVariable("reviewId") Long reviewId, @RequestBody CommentRequest request) {
        commentService.postComment(reviewId, request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_COMMENT_SUCCESS));
    }


    @ApiOperation(value = "댓글 수정")
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<ResultResponse> putComment(@PathVariable("commentId") Long commentId, @RequestBody CommentRequest request) {
        commentService.putComment(commentId, request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PUT_COMMENT_SUCCESS));
    }


    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ResultResponse> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_COMMENT_SUCCESS));
    }


    @ApiOperation(value = "대댓글 작성")
    @PostMapping("/re-comment/{upperCommentId}")
    public ResponseEntity<ResultResponse> postReComment(@PathVariable("upperCommentId") Long upperCommentId, @RequestBody CommentRequest request) {
        commentService.postReComment(upperCommentId, request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_RECOMMENT_SUCCESS));
    }


    @ApiOperation(value = "대댓글 수정")
    @PutMapping("/re-comment/{commentId}")
    public ResponseEntity<ResultResponse> putReComment(@PathVariable("commentId") Long commentId, @RequestBody CommentRequest request) {
        commentService.putComment(commentId, request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.PUT_RECOMMENT_SUCCESS));
    }


    @ApiOperation(value = "대댓글 삭제")
    @DeleteMapping("/re-comment/{commentId}")
    public ResponseEntity<ResultResponse> deleteReComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteReComment(commentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_RECOMMENT_SUCCESS));
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
