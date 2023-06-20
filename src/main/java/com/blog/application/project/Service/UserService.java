package com.blog.application.project.Service;

import com.blog.application.project.Payload.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, Integer id);
    UserDTO getUserById(Integer id);
    List<UserDTO> getAllUsers();
    void deleteUser(Integer id);

    UserDTO registerNewUser(UserDTO userDTO);
}
