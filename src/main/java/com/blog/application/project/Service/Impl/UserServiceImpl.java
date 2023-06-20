package com.blog.application.project.Service.Impl;

import com.blog.application.project.Exception.ResourceNotFoundException;
import com.blog.application.project.Model.Role;
import com.blog.application.project.Model.User;
import com.blog.application.project.Payload.UserDTO;
import com.blog.application.project.Repository.RoleRepository;
import com.blog.application.project.Repository.UserRepository;
import com.blog.application.project.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userDtoToUser(userDTO);
        User user1 = userRepository.save(user);
        UserDTO userDTO1 = userToUserDto(user);
        return userDTO1;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", " ID ", id));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User savedUser = userRepository.save(user);

        UserDTO userDto = userToUserDto(savedUser);
        return userDTO;
    }

    @Override
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", " ID ", id));

        UserDTO userDTO = userToUserDto(user);
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = userRepository.findAll().stream().map(user -> userToUserDto(user)).collect(Collectors.toList());
        return userDTOList;
    }

    @Override
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", " ID ", id));

        userRepository.deleteById(id);
    }

    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role userRole = roleRepository.findByRoleId(502).orElseThrow(() -> new ResourceNotFoundException("Role", "ID", 502));

        user.getRoles().add(userRole);

        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public User userDtoToUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

    public UserDTO userToUserDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
