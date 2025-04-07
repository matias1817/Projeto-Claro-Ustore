package com.claro.projeto.model.machine;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.projeto.model.user.User;
import com.claro.projeto.model.user.UserService;
import com.claro.projeto.util.execeptions.EntidadeNaoEncontradaExeception;
import com.claro.projeto.util.execeptions.LimiteDeVMsAtingidoExeception;

import jakarta.transaction.Transactional;

@Service
public class VirtualMachineService {

    @Autowired
    private VirtualMachineRepository repository;

    @Autowired
    private UserService userService;

    @Transactional
    public VirtualMachine save (VirtualMachine machine){
        
    User user = this.userService.findByID(machine.getUser().getId());
    List<VirtualMachine> list = this.findByUser(user.getId());

    if (user.getLimitVm() <= list.size()) {
        throw new LimiteDeVMsAtingidoExeception("Limite de VMs atingido para o usuário: " + user.getUsername());
    }
        machine.setHabilitado(true);
        machine.setVersion(1L);
        machine.setCreationDate(LocalDate.now());
       VirtualMachine vm = this.repository.save(machine);
        return vm;
    }

    @Transactional
    public void update(Long id,VirtualMachine updatedMachine) {
       VirtualMachine machine = this.findByID(id);

        machine.setCpu(updatedMachine.getCpu());
        machine.setDisplayName(updatedMachine.getDisplayName());
        machine.setMemory(updatedMachine.getMemory());
        machine.setStatus(updatedMachine.getStatus());
        machine.setLastModificationDate(LocalDate.now());

        machine.setVersion(machine.getVersion()+1);
        this.repository.save(machine);
    }

    public List<VirtualMachine> findAll(){
        return this.repository.findAll();    
    }

    public VirtualMachine findByID(Long id) {
        return this.repository.findById(id).orElseThrow(() ->
            new EntidadeNaoEncontradaExeception("Machine", id));
    }

    public List<VirtualMachine> findByUser(Long id){
        User user = this.userService.findByID(id);
        return this.repository.findByUser(user);
    }

    @Transactional
    public void delete(Long id){
        
        VirtualMachine machine = this.findByID(id);

        machine.setDisplayName(null);

        machine.setHabilitado(Boolean.FALSE);

        this.repository.save(machine);
    }

}
