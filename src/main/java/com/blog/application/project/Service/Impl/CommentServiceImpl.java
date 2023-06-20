package com.blog.application.project.Service.Impl;

import com.blog.application.project.Exception.ResourceNotFoundException;
import com.blog.application.project.Model.Comment;
import com.blog.application.project.Model.Post;
import com.blog.application.project.Model.User;
import com.blog.application.project.Payload.CommentDTO;
import com.blog.application.project.Repository.CommentRepository;
import com.blog.application.project.Repository.PostRepository;
import com.blog.application.project.Repository.UserRepository;
import com.blog.application.project.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer userId, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        Comment comment = modelMapper.map(commentDTO, Comment.class);

        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Integer commentId) {
        Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));

        comment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(comment);

        return modelMapper.map(updatedComment, CommentDTO.class);
    }

    @Override
    public CommentDTO getCommentById(Integer commentId) {
        Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));

        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

        return commentDTO;
    }

    @Override
    public List<CommentDTO> getAllComment() {
        List<CommentDTO> commentDTOList = commentRepository.findAll().stream().map((comments) -> modelMapper.map(comments, CommentDTO.class)).collect(Collectors.toList());

        return commentDTOList;
    }

    @Override
    public List<CommentDTO> getCommentByPostId(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));

        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream().map((comment) -> modelMapper.map(comment, CommentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getCommentByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        List<Comment> user1 = commentRepository.findByUser(user);

        return user1.stream().map((comment) -> modelMapper.map(comment, CommentDTO.class)).collect(Collectors.toList());
    }
}
