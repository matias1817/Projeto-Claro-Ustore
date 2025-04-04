package com.claro.projeto.api.maquina;

import com.claro.projeto.model.maquina.Maquina;
import com.claro.projeto.util.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaquinaRequest {

    private String displayName;

    private String status;

    private Integer cpu;

    private Integer memory;

    public Maquina build() {
        return Maquina.builder()
        .cpu(cpu)
        .displayName(displayName)
        .memory(memory)
        .status(Status.valueOf(status.toUpperCase()))
        .build();
    }

}
