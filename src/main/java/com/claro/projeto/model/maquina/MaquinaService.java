package com.claro.projeto.model.maquina;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claro.projeto.util.execeptions.EntidadeNaoEncontradaExeception;

import jakarta.transaction.Transactional;

@Service
public class MaquinaService {

    @Autowired
    private MaquinaRepository repository;

    @Transactional
    public Maquina save (Maquina maquina){

        maquina.setHabilitado(true);
        maquina.setVersao(1L);
        maquina.setDataCriacao(LocalDate.now());

        Maquina maquina2 = repository.save(maquina);

        return maquina2;
    }

    @Transactional
    public void update(Long id, Maquina maquinaEditada) {
        Maquina maquina = this.findById(id);

        maquina.setCpu(maquinaEditada.getCpu());
        maquina.setDisplayName(maquinaEditada.getDisplayName());
        maquina.setMemory(maquinaEditada.getMemory());
        maquina.setStatus(maquinaEditada.getStatus());

        maquina.setVersao(maquina.getVersao()+1);
        repository.save(maquina);
    }

    public List<Maquina> findAll(){
        return repository.findAll();    
    }

    public Maquina findById(Long ID){

        Optional<Maquina> maquina = repository.findById(ID);
        
        if(maquina.isPresent()){
        return maquina.get();
        } else {
            throw new EntidadeNaoEncontradaExeception("Maquina", ID);
        }
    }

    @Transactional
    public void delete(Long id){
        
        Maquina maquina = this.findById(id);

        maquina.setHabilitado(Boolean.FALSE);
        maquina.setVersao(maquina.getVersao()+1);
        maquina.setCpu(0);
        maquina.setMemory(0);
        maquina.setDisplayName("");
        maquina.setStatus(null);

        repository.save(maquina);
    }


}
