package com.blog.application.project.Repository;

import com.blog.application.project.Model.Category;
import com.blog.application.project.Model.Comment;
import com.blog.application.project.Model.Post;
import com.blog.application.project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("select p from Post as p where p.title LIKE :key")
    List<Post> searchPostByTitle(@Param("key") String title);

    Post findByComments(Comment comment);
}
