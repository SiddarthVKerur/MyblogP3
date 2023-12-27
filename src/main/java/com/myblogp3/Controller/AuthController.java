package com.myblogp3.Controller;

import com.myblogp3.Entity.User;
import com.myblogp3.Repository.UserRepository;
import com.myblogp3.payload.SignInDto;
import com.myblogp3.payload.SignUpDto;
import com.myblogp3.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>(signUpDto.getEmail() + " already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>(signUpDto.getUsername() + " already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userRepository.save(user);
        emailService.sendMail(signUpDto.getEmail(),"testing","successfully");
        return new ResponseEntity<>("successfully registered", HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody SignInDto signInDto) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(signInDto.getUsernameOrEmail(), signInDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return new ResponseEntity<>("user sign-in successfully", HttpStatus.OK);
    }


}
