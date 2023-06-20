package com.blog.application.project.Security;

import com.blog.application.project.Exception.ApiException;
import com.blog.application.project.Exception.ResourceNotFoundException;
import com.blog.application.project.Model.User;
import com.blog.application.project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ApiException("User Not Found with Email: " + username));
        return user;
    }
}
