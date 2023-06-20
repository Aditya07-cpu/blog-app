package com.blog.application.project.Payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Integer postId;

    private String title;

    private String content;

    private Date addedDate;

    private String imageName;

    private CategoryDTO category;

    private UserDTO user;

    private List<CommentDTO> comments;
}
