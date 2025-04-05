package com.claro.projeto.api.machine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claro.projeto.model.machine.VirtualMachine;
import com.claro.projeto.model.machine.VirtualMachineService;
import com.claro.projeto.model.user.User;
import com.claro.projeto.model.user.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/machine")
@CrossOrigin
public class VirtualMachineController {

    @Autowired
    private VirtualMachineService machineService;
    
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<VirtualMachineResource> save(@RequestBody @Valid VirtualMachineRequest request) {
        VirtualMachine machine = request.build();
        User user = userService.findByID(request.getUserId());
        machine.setUser(user);
        machine = this.machineService.save(machine);
        VirtualMachineResource response = new VirtualMachineResource(machine);
        return new ResponseEntity<VirtualMachineResource>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VirtualMachineResource> findByID(@PathVariable Long id){
        VirtualMachine machine = this.machineService.findByID(id);
        VirtualMachineResource response = new VirtualMachineResource(machine);
        return ResponseEntity.ok(response);
    }
    
    // a paginação ....
    // @GetMapping
	// public ResponseEntity<Page<VirtualMachineResource>> getPage(.... tararara) throws Exception {
	// 	Page<VirtualMachineResourceImpl> page = this.listVirtualMachineService.list();
	// 	return ResponseEntity.of(Optional.ofNullable(page));
	// }
	
    @GetMapping()
    public ResponseEntity<List<VirtualMachineResource>> findAll() {
        List<VirtualMachine> machines = this.machineService.findAll();
        List<VirtualMachineResource> responses = machines.stream()
        .map(VirtualMachineResource::new)
        .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<VirtualMachineResource>> findByUser(@PathVariable("id") Long id) {
        List<VirtualMachine> machines = this.machineService.findByUser(id);
        List<VirtualMachineResource> responses = machines.stream()
        .map(VirtualMachineResource::new)
        .toList();
        return ResponseEntity.ok(responses);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VirtualMachine> update(@PathVariable("id") Long id, @RequestBody VirtualMachineRequest request){
        this.machineService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.machineService.delete(id);
        return ResponseEntity.ok().build();
    }    
    
}
