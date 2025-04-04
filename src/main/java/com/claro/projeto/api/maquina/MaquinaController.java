package com.claro.projeto.api.maquina;

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

import com.claro.projeto.model.maquina.Maquina;
import com.claro.projeto.model.maquina.MaquinaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/cliente")
@CrossOrigin
public class MaquinaController {

    @Autowired
    private MaquinaService maquinaService;

    @PostMapping
    public ResponseEntity<Maquina> save(@RequestBody @Valid MaquinaRequest request) {
        Maquina maquina = maquinaService.save(request.build());
        return new ResponseEntity<Maquina>(maquina, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Maquina findById(@PathVariable Long id){
        return maquinaService.findById(id);
    }

    @GetMapping()
    public List<Maquina> findAll() {
        return maquinaService.findAll();
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Maquina> update(@PathVariable("id") Long id, @RequestBody MaquinaRequest request){
        maquinaService.update(id, request.build());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        maquinaService.delete(id);
        return ResponseEntity.ok().build();
    }    
    
}
