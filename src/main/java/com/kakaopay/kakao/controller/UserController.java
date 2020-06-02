package com.kakaopay.kakao.controller;

import com.kakaopay.kakao.model.user.Role;
import com.kakaopay.kakao.model.user.RoleNames;
import com.kakaopay.kakao.model.user.User;
import com.kakaopay.kakao.exception.AppException;
import com.kakaopay.kakao.exception.ResourceNotFoundException;
import com.kakaopay.kakao.repository.RoleRepository;
import com.kakaopay.kakao.repository.UserRepository;
import com.kakaopay.kakao.security.JwtTokenProvider;
import com.kakaopay.kakao.service.UserService;
import com.kakaopay.kakao.service.UtilService;
import com.kakaopay.kakao.util.CouponCreation;
import com.kakaopay.kakao.vo.ApiResponse;
import com.kakaopay.kakao.vo.JwtAuthenticationResponse;
import com.kakaopay.kakao.vo.SignInRequest;
import com.kakaopay.kakao.vo.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    UtilService utilService;

    // 회원 가입 API
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleNames.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));
        user.setRoles(Collections.singleton(userRole));

        User result = userService.createUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/signin")
                .buildAndExpand(result.getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

    }

    // 로그인 API
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

//    @GetMapping("/{email}")
//    public ResponseEntity<User> retrieveByEmail(HttpServletRequest request) {
//
//        User user = userService.findByEmail(request.getUserPrincipal().getName())
//                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", request.getUserPrincipal().getName()));
//        return new ResponseEntity<User>(user, HttpStatus.OK);
//    }

}
