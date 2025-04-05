package com.claro.projeto.model.machine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {
    
}