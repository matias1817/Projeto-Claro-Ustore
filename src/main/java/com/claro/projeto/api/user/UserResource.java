package com.claro.projeto.api.user;

import java.time.LocalDate;
import com.claro.projeto.model.user.User;

public record UserResource(
    String cpf,
    String email,
    String name,
    LocalDate creationDate
) {
    public UserResource(User user) {
        this(
            user.getCpf(),
            user.getEmail(),
            user.getUsername(),
            user.getCreationDate()
        );			
    }
}
