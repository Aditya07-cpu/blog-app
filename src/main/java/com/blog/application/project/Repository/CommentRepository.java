package com.blog.application.project.Repository;

import com.blog.application.project.Model.Comment;
import com.blog.application.project.Model.Post;
import com.blog.application.project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<Comment> findByCommentId(Integer commentId);

    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
