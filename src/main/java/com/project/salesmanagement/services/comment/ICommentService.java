package com.project.salesmanagement.services.comment;

import com.project.salesmanagement.dtos.CommentDTO;
import com.project.salesmanagement.exceptions.DataNotFoundException;
import com.project.salesmanagement.models.Comment;
import com.project.salesmanagement.responses.comment.CommentResponse;

import java.util.List;

public interface ICommentService {
    Comment insertComment(CommentDTO comment);

    void deleteComment(Long commentId);
    void updateComment(Long id, CommentDTO commentDTO) throws DataNotFoundException;

    List<CommentResponse> getCommentsByUserAndProduct(Long userId, Long productId);
    List<CommentResponse> getCommentsByProduct(Long productId);
    void generateFakeComments() throws Exception;
}
