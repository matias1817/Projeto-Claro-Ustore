package com.claro.projeto.api.acess;

import com.claro.projeto.model.user.User;
import com.claro.projeto.model.user.UserService;
import com.claro.projeto.security.JwtTokenProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController controller;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSigninSuccess() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("zezin");
        request.setPassword("123");

        User user = new User();
        user.setId(1L);
        user.setUsername("zezin");
        user.setRoles(List.of("ROLE_USER"));

        when(userService.findByUsername("zezin")).thenReturn(user);
        when(jwtTokenProvider.createToken(anyString(), anyList())).thenReturn("mockedToken");
        when(jwtTokenProvider.createRefreshToken(anyString())).thenReturn("mockedRefreshToken");

        Map<Object, Object> result = controller.signin(request);


        assertEquals("zezin", result.get("username"));
        assertEquals(1L, result.get("id"));
        assertEquals("mockedToken", result.get("token"));
        assertEquals("mockedRefreshToken", result.get("refresh"));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
