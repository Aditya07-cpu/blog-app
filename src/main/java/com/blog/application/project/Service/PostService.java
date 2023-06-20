package com.blog.application.project.Service;

import com.blog.application.project.Payload.PostDTO;
import com.blog.application.project.Payload.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    PostDTO updatePost(PostDTO postDTO, Integer postId);

    void deletePost(Integer postId);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostDTO getPostById(Integer postId);

    List<PostDTO> getPostByCategoryId(Integer categoryId);

    List<PostDTO> getPostByUserId(Integer userId);

    List<PostDTO> searchPostByTitle(String title);

    PostDTO getPostByCommentId(Integer commentId);
}
