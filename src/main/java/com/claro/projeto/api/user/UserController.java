package com.claro.projeto.api.user;

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

import com.claro.projeto.model.user.User;
import com.claro.projeto.model.user.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResource> save(@RequestBody @Valid UserRequest request) {
        User user = this.userService.save(request.build());
        UserResource response = new UserResource(user);
        return new ResponseEntity<UserResource>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> findByID(@PathVariable Long id){
        User user = this.userService.findByID(id);
        UserResource response = new UserResource(user);
        return ResponseEntity.ok(response);
    }
    
    // a paginação ....
    // @GetMapping
	// public ResponseEntity<Page<VirtualMachineResource>> getPage(.... tararara) throws Exception {
	// 	Page<VirtualMachineResourceImpl> page = this.listVirtualMachineService.list();
	// 	return ResponseEntity.of(Optional.ofNullable(page));
	// }
	
    @GetMapping()
    public ResponseEntity<List<UserResource>> findAll() {
        List<User> user = this.userService.findAll();
        List<UserResource> responses = user.stream()
        .map(UserResource::new)
        .toList();

        return ResponseEntity.ok(responses);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody UserRequest request){
        this.userService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.ok().build();
    }



    
}
