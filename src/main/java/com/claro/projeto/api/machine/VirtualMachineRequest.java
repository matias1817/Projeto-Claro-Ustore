package com.claro.projeto.api.machine;

import com.claro.projeto.model.machine.VirtualMachine;
import com.claro.projeto.util.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VirtualMachineRequest {

    private String displayName;

    private String status;

    private Integer cpu;

    private Integer memory;

    private Long userId;

    public VirtualMachine build() {
        return VirtualMachine.builder()
        .cpu(cpu)
        .displayName(displayName)
        .memory(memory)
    
        .status(Status.valueOf(status.toUpperCase()))
        .build();
    }

}
