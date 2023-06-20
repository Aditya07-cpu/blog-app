package com.blog.application.project.Service.Impl;

import com.blog.application.project.Exception.ResourceNotFoundException;
import com.blog.application.project.Model.Category;
import com.blog.application.project.Model.Comment;
import com.blog.application.project.Model.Post;
import com.blog.application.project.Model.User;
import com.blog.application.project.Payload.PostDTO;
import com.blog.application.project.Payload.PostResponse;
import com.blog.application.project.Repository.CategoryRepository;
import com.blog.application.project.Repository.CommentRepository;
import com.blog.application.project.Repository.PostRepository;
import com.blog.application.project.Repository.UserRepository;
import com.blog.application.project.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        Post post = modelMapper.map(postDTO, Post.class);

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));

        post.setUser(user);
        post.setCategory(category);
        post.setImageName("null");
        post.setAddedDate(new Date());

        Post savedPost = postRepository.save(post);

        return modelMapper.map(savedPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());

        PostDTO postDTO1 = modelMapper.map(post, PostDTO.class);

        return postDTO1;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));

        postRepository.deleteById(postId);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<PostDTO> postDTOList = posts.getContent().stream().map((post -> modelMapper.map(post, PostDTO.class))).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setData(postDTOList);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));

        PostDTO postDTO = modelMapper.map(post, PostDTO.class);

        return postDTO;
    }

    @Override
    public List<PostDTO> getPostByCategoryId(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));

        List<Post> category1 = postRepository.findByCategory(category);

        List<PostDTO> postDTOList = category1.stream().map((categorys) -> modelMapper.map(categorys, PostDTO.class)).collect(Collectors.toList());

        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        List<Post> user1 = postRepository.findByUser(user);

        List<PostDTO> userDTOList = user1.stream().map((users) -> modelMapper.map(users, PostDTO.class)).collect(Collectors.toList());

        return userDTOList;
    }

    @Override
    public List<PostDTO> searchPostByTitle(String keyword) {
        List<Post> posts = postRepository.searchPostByTitle("%"+keyword+"%");

        List<PostDTO> postDTOList = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());

        return postDTOList;
    }

    @Override
    public PostDTO getPostByCommentId(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));

        Post post = postRepository.findByComments(comment);

        return modelMapper.map(post, PostDTO.class);
    }
}
