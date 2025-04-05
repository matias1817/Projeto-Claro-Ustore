package com.claro.projeto.model.machine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.claro.projeto.model.user.User;


//como não tem nehuma query implementada por min não fiz o teste 
public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {

    List<VirtualMachine> findByUser(User user);
    
}