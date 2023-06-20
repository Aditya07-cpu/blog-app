package com.blog.application.project.Controller;

import com.blog.application.project.Payload.CommentDTO;
import com.blog.application.project.Payload.ResponseObject;
import com.blog.application.project.Service.Impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/user/{userId}/posts/{postId}/comment")
    public ResponseEntity<ResponseObject> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer userId, @PathVariable Integer postId) {
        ResponseObject responseObject = new ResponseObject();

        CommentDTO commentDto = commentService.createComment(commentDTO, userId, postId);

        responseObject.setMessage("Comment Successfully Created");
        responseObject.setSuccess(true);
        responseObject.setData(commentDto);

        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ResponseObject> deleteComment(@PathVariable Integer commentId) {
        ResponseObject responseObject = new ResponseObject();

        commentService.deleteComment(commentId);

        responseObject.setMessage("Comment With Id: " + commentId + " Deleted Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(null);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<ResponseObject> updateComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer commentId) {
        ResponseObject responseObject = new ResponseObject();

        CommentDTO updatedComment = commentService.updateComment(commentDTO, commentId);

        responseObject.setMessage("Comment With Id: " + commentId + " Updated Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(updatedComment);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<ResponseObject> getCommentById(@PathVariable Integer commentId) {
        ResponseObject responseObject = new ResponseObject();

        CommentDTO commentDTO = commentService.getCommentById(commentId);

        responseObject.setMessage("Comment With Id: " + commentId + " Found Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(commentDTO);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/comment")
    public ResponseEntity<ResponseObject> getAllComment() {
        ResponseObject responseObject = new ResponseObject();

        List<CommentDTO> commentDTOList = commentService.getAllComment();

        responseObject.setMessage("Fetched All Comments");
        responseObject.setSuccess(true);
        responseObject.setData(commentDTOList);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comment")
    public ResponseEntity<ResponseObject> getCommentByPostId(@PathVariable Integer postId) {
        ResponseObject responseObject = new ResponseObject();

        List<CommentDTO> commentDTOList = commentService.getCommentByPostId(postId);

        responseObject.setMessage("Comment With Post Id: " + postId + " Found Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(commentDTOList);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/comment")
    public ResponseEntity<ResponseObject> getCommentByUserId(@PathVariable Integer userId) {
        ResponseObject responseObject = new ResponseObject();

        List<CommentDTO> commentDTOList = commentService.getCommentByUserId(userId);

        responseObject.setMessage("Comment With User Id: " + userId + " Found Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(commentDTOList);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}