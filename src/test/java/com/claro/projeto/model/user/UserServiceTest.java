package com.claro.projeto.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.claro.projeto.util.execeptions.EntidadeNaoEncontradaExeception;
    

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("zezin");
        user.setPassword("123456");
        user.setCpf("54553618052");

        when(passwordEncoder.encode("123456")).thenReturn("senhaHash");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.save(user);

        assertEquals("zezin", result.getUsername());
        assertEquals("senhaHash", result.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("zezin");

        when(userRepository.findByUsername("zezin")).thenReturn(user);

        User result = userService.findByUsername("zezin");

        assertEquals("zezin", result.getUsername());
        verify(userRepository, times(1)).findByUsername("zezin");
    }

    @Test
    public void testFindByIDExists() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findByID(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testFindByIDNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaExeception.class, () -> {
            userService.findByID(2L);
        });
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setId(1L);
        user.setHabilitado(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        assertFalse(user.getHabilitado());
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdate() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setVersion(1L);
        existingUser.setUsername("ze");
        existingUser.setCpf("54553618052");
        existingUser.setEmail("ze@email.com");

        User updatedUser = new User();
        updatedUser.setUsername("zezinhos");
        updatedUser.setCpf("92446973000");
        updatedUser.setEmail("zezinho@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.update(1L, updatedUser);

        assertEquals("92446973000", existingUser.getCpf());
        assertEquals("zezinho@email.com", existingUser.getEmail());
        assertEquals(2L, existingUser.getVersion());
        assertNotNull(existingUser.getLastModificationDate());

        verify(userRepository).save(existingUser);
    }

}
    