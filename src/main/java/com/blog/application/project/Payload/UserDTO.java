package com.blog.application.project.Payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserDTO {

    private int id;

    @NotEmpty
    @Size(min = 4, message = "Username must be of 4 characters !!")
    private String name;

    @Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message="Please provide a valid email address")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be of 3 characters and max of 10 characters !!")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{3,10}$",  message = "It contains at least 3 characters and at most 10 characters.\nIt contains at least one digit.\nIt contains at least one upper case alphabet.\nIt contains at least one lower case alphabet.\nIt contains at least one special character which includes !@#$%&*()-+=^.\nIt doesn’t contain any white space.")
    private String password;

    @NotEmpty
    private String about;

    List<RoleDTO> roles = new ArrayList<>();
}
