package com.blog.application.project.Controller;

import com.blog.application.project.Payload.ResponseObject;
import com.blog.application.project.Payload.UserDTO;
import com.blog.application.project.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody UserDTO userDTO) {
        ResponseObject responseObject = new ResponseObject();

        UserDTO userDto = userService.createUser(userDTO);
        responseObject.setMessage("User Created Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(userDto);
        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAllUser() {
        ResponseObject responseObject = new ResponseObject();

        List<UserDTO> userDTOList = userService.getAllUsers();
        responseObject.setMessage("Fetched All Users");
        responseObject.setSuccess(true);
        responseObject.setData(userDTOList);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable int userId) {
        ResponseObject responseObject = new ResponseObject();

        UserDTO userDTO = userService.getUserById(userId);

        responseObject.setMessage("User With Id: " + userId + " is Found");
        responseObject.setSuccess(true);
        responseObject.setData(userDTO);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseObject> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable int userId) {
        ResponseObject responseObject = new ResponseObject();

        UserDTO updateUser = userService.updateUser(userDTO, userId);
        responseObject.setMessage("User With Id: " + userId + " Updated Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(updateUser);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable int userId) {
        ResponseObject responseObject = new ResponseObject();

        userService.deleteUser(userId);

        responseObject.setMessage("User With Id: " + userId + " Deleted Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(null);

        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}
