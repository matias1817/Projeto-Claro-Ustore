package com.claro.projeto.api.user;

import com.claro.projeto.model.user.User;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @Email
    private String email;

    private String cpf;

    private String password;

    private String name;

    private Integer limiteVm;

    public User build() {
        return User.builder()
        .email(email)
        .cpf(cpf)
        .limitVm(limiteVm)
        .username(name)
        .password(password)
        .build();
    }

}
