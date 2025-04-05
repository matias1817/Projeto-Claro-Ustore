package com.claro.projeto.model.machine;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.projeto.util.execeptions.EntidadeNaoEncontradaExeception;

import jakarta.transaction.Transactional;

@Service
public class VirtualMachineService {

    @Autowired
    private VirtualMachineRepository repository;

    @Transactional
    public VirtualMachine save (VirtualMachine machine){

        machine.setEnabled(true);
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

    @Transactional
    public void delete(Long id){
        
        VirtualMachine machine = this.findByID(id);

        machine.setEnabled(Boolean.FALSE);

        this.repository.save(machine);
    }

}
