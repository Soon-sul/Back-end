package com.example.soonsul.liquor.controller;

import com.example.soonsul.liquor.dto.CommentRequest;
import com.example.soonsul.liquor.service.CommentService;
import com.example.soonsul.response.result.ResultCode;
import com.example.soonsul.response.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags="Comment")
@RestController
@RequiredArgsConstructor
@RequestMapping("/liquor")
public class CommentController {
    private final CommentService commentService;

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


    @ApiOperation(value = "댓글 좋아요 추가")
    @PostMapping("/comment/{commentId}/like")
    public ResponseEntity<ResultResponse> postCommentLike(@PathVariable("commentId") Long commentId) {
        commentService.postCommentLike(commentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_COMMENT_LIKE_SUCCESS));
    }


    @ApiOperation(value = "댓글 좋아요 삭제")
    @DeleteMapping("/comment/{commentId}/like")
    public ResponseEntity<ResultResponse> deleteCommentLike(@PathVariable("commentId") Long commentId) {
        commentService.deleteCommentLike(commentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_COMMENT_LIKE_SUCCESS));
    }


    @ApiOperation(value = "해당 댓글의 좋아요 개수 조회")
    @GetMapping("/comment/{commentId}/like")
    public ResponseEntity<ResultResponse> getCommentLike(@PathVariable("commentId") Long commentId) {
        final Integer data= commentService.getCommentLike(commentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COMMENT_LIKE_SUCCESS, data));
    }

}
