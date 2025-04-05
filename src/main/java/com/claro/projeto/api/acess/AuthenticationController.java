package com.claro.projeto.api.acess;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claro.projeto.model.user.User;
import com.claro.projeto.model.user.UserService;
import com.claro.projeto.security.JwtTokenProvider;


@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public Map<Object, Object> signin(@RequestBody AuthenticationRequest data) {

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword()));

            User user = this.userService.findByUsername(data.getUsername());
            String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
            String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername());

            Map<Object, Object> model = new HashMap<>();
            model.put("username", user.getUsername());
            model.put("id", user.getId());
            model.put("token", token);
            model.put("refresh", refreshToken);

            return model;

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

}