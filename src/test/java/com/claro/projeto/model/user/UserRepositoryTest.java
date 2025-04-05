package com.claro.projeto.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("zezin");
        user.setPassword("123456");
        user.setCpf("54553618052");
        user.setEmail("zezin@email.com");
        userRepository.save(user);

        User result = userRepository.findByUsername("zezin");

        assertNotNull(result);
        assertEquals("zezin", result.getUsername());
    }

}
