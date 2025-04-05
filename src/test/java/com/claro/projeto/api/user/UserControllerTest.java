package com.claro.projeto.api.user;
import com.claro.projeto.model.user.User;
import com.claro.projeto.model.user.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        UserRequest request = new UserRequest();
        request.setName("jonatas");
        request.setPassword("123456");
        request.setEmail("jo@email.com");

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("jonatas");

        when(userService.save(any(User.class))).thenReturn(mockUser);

        ResponseEntity<UserResource> response = userController.save(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jonatas", response.getBody().name());
    }

    @Test
    public void testFindByID() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("davi");

        when(userService.findByID(1L)).thenReturn(mockUser);

        ResponseEntity<UserResource> response = userController.findByID(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("davi", response.getBody().name());
    }

    @Test
    public void testUpdate() {
        UserRequest request = new UserRequest();
        request.setName("saul");
        request.setEmail("saul@email.com");

        doNothing().when(userService).update(eq(1L), any(User.class));

        ResponseEntity<User> response = userController.update(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).update(eq(1L), any(User.class));
    }

    @Test
    public void testDelete() {
        doNothing().when(userService).delete(1L);

        ResponseEntity<Void> response = userController.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).delete(1L);
    }
}