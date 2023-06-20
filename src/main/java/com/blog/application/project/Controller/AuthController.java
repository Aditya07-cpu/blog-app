package com.blog.application.project.Controller;

import com.blog.application.project.Exception.ApiException;
import com.blog.application.project.Payload.JwtAuthRequest;
import com.blog.application.project.Payload.JwtAuthResponse;
import com.blog.application.project.Payload.ResponseObject;
import com.blog.application.project.Payload.UserDTO;
import com.blog.application.project.Security.CustomerUserDetailService;
import com.blog.application.project.Security.JwtTokenHelper;
import com.blog.application.project.Service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {

        authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());

        UserDetails userDetails = customerUserDetailService.loadUserByUsername(jwtAuthRequest.getUsername());

        String token = jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(token);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (BadCredentialsException e) {
            System.out.println("Invalid Details");
            throw new ApiException("Invalid username or password !!");
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseObject> registerNewUser(@RequestBody UserDTO userDTO) {
        ResponseObject responseObject = new ResponseObject();

        UserDTO newUser = userService.registerNewUser(userDTO);

        responseObject.setMessage("User Registered Successfully");
        responseObject.setSuccess(true);
        responseObject.setData(newUser);

        return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
    }
}