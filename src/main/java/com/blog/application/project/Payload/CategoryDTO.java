package com.blog.application.project.Payload;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO {

    private Integer categoryId;

    @NotBlank
    @Size(min = 4, message = "Min size of Category title is 4")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, message = "min size of Category desc is 10")
    private String categoryDescription;
}
