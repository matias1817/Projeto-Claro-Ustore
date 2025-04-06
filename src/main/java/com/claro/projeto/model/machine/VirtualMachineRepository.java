package com.claro.projeto.model.machine;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.claro.projeto.model.user.User;


//como não tem nehuma query implementada por min não fiz o teste 
public interface VirtualMachineRepository extends JpaRepository<VirtualMachine, Long> {
    @Query("SELECT vm FROM VirtualMachine vm WHERE vm.user = :user AND vm.habilitado = true")
    List<VirtualMachine> findByUser(@Param("user") User user);

    //List<VirtualMachine> findByUser(User user);
    
}