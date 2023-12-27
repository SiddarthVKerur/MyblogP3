package com.myblogp3.Service;

import com.myblogp3.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    void deleteComment(long postId, long commentId);

    List<CommentDto> getPostComments(long postId);

    CommentDto updateComment(long commentId, CommentDto commentDto);
}
