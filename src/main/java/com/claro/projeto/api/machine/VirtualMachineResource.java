package com.claro.projeto.api.machine;

import java.time.LocalDate;
import com.claro.projeto.model.machine.VirtualMachine;
import com.claro.projeto.util.enums.Status;

public record VirtualMachineResource(
    Status status,
    String displayName,
    Integer cpu,
    Integer memory,
    LocalDate dataCriacao
) {
    public VirtualMachineResource(VirtualMachine virtualMachine) {
        this(
            virtualMachine.getStatus(),
            virtualMachine.getDisplayName(),
            virtualMachine.getCpu(),
            virtualMachine.getMemory(),
            virtualMachine.getCreationDate()
        );			
    }
}
