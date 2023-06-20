package com.blog.application.project.Controller;

import com.blog.application.project.Payload.PostDTO;
import com.blog.application.project.Payload.PostResponse;
import com.blog.application.project.Payload.ResponseObject;
import com.blog.application.project.Service.FileService;
import com.blog.application.project.Service.Impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<ResponseObject> createPost(@RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        ResponseObject responseObject = new ResponseObject();

        PostDTO postDto = postService.createPost(postDTO, userId, categoryId);

        responseObject.setMessage("Post Created Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(postDto);

        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<ResponseObject> getPostByUserId(@PathVariable Integer userId) {
        ResponseObject responseObject = new ResponseObject();

        List<PostDTO> postDTOList = postService.getPostByUserId(userId);

        responseObject.setMessage("Post With User Id: " + userId + " Found Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(postDTOList);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<ResponseObject> getPostByCategoryId(@PathVariable Integer categoryId) {
        ResponseObject responseObject = new ResponseObject();

        List<PostDTO> postDTOList = postService.getPostByCategoryId(categoryId);

        responseObject.setMessage("Post With Category Id: " + categoryId + " Found Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(postDTOList);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("posts/comment/{commentId}")
    public ResponseEntity<ResponseObject> getPostByCommentId(@PathVariable Integer commentId) {
        ResponseObject responseObject = new ResponseObject();

        PostDTO postDTO = postService.getPostByCommentId(commentId);

        responseObject.setMessage("Post With Comment Id: " + commentId + " Found Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(postDTO);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ResponseObject> deletePost(@PathVariable Integer postId) {
        ResponseObject responseObject = new ResponseObject();

        postService.deletePost(postId);

        responseObject.setMessage("Post With Id: " + postId + " Deleted Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(null);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<ResponseObject> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer postId) {
        ResponseObject responseObject = new ResponseObject();

        PostDTO postDTO1 = postService.updatePost(postDTO, postId);

        responseObject.setMessage("Post With Id: " + postId + " Updated Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(postDTO1);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponseObject> getPostById(@PathVariable Integer postId) {
        ResponseObject responseObject = new ResponseObject();

        PostDTO postDTO = postService.getPostById(postId);

        responseObject.setMessage("Post With Id: " + postId + " Fetched Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(postDTO);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        ResponseObject responseObject = new ResponseObject();

        PostResponse post = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<ResponseObject> searchPostByTitle(@PathVariable String keyword) {
        ResponseObject responseObject = new ResponseObject();

        List<PostDTO> postDTO = postService.searchPostByTitle(keyword);

        responseObject.setMessage("Post With Keyword: " + keyword + " Found Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(postDTO);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile file, @PathVariable Integer postId) throws IOException {
        PostDTO postDTO = postService.getPostById(postId);

        String fileName = fileService.uploadImage(path, file);

        postDTO.setImageName(fileName);

        PostDTO updatedPost = postService.updatePost(postDTO, postId);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("/posts/image/download/{imageName}")
    public void downloadPostImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
