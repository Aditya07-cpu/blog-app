package com.blog.application.project.Service;

import com.blog.application.project.Payload.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer userId, Integer postId);

    void deleteComment(Integer commentId);

    CommentDTO updateComment(CommentDTO commentDTO, Integer commentId);

    CommentDTO getCommentById(Integer commentId);

    List<CommentDTO> getAllComment();

    List<CommentDTO> getCommentByPostId(Integer postId);

    List<CommentDTO> getCommentByUserId(Integer userId);
}
