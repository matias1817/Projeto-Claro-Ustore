package com.claro.projeto.model.machine;

import org.springframework.data.jpa.repository.JpaRepository;

//como não tem nehuma query implementada por min não fiz o teste 
public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {
    
}