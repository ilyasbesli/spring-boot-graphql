package com.communitygaming.controller;

import com.communitygaming.dto.AuthToken;
import com.communitygaming.dto.UserDto;
import com.communitygaming.model.User;
import com.communitygaming.security.jwt.AuthRequest;
import com.communitygaming.security.jwt.TokenProvider;
import com.communitygaming.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * Controller to authenticate users.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserServiceImpl userAuthService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        log.debug("Request for authenticate user {}", authRequest.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                authRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthToken authToken = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(authToken);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        User user = userAuthService.createUser(userDto);
        Map<String, Object> responseMap = Map.of("id", user.getId(), "email", user.getEmail());
        return ResponseEntity.ok(responseMap);
    }

}
