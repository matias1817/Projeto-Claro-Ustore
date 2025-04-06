package com.claro.projeto.model.user;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.claro.projeto.util.execeptions.EntidadeNaoEncontradaExeception;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User save (User user){

        user.setHabilitado(true);
        user.setVersion(1L);
        user.setCreationDate(LocalDate.now());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        User savedUser = this.repository.save(user);
        return savedUser;
    }

    @Transactional
    public void update(Long id,User updatedUser) {
        User user = this.findByID(id);

        user.setCpf(updatedUser.getCpf());
        user.setEmail(updatedUser.getEmail());
        user.setLimitVm(updatedUser.getLimitVm());

        user.setVersion(user.getVersion()+1);
        user.setLastModificationDate(LocalDate.now());

        this.repository.save(user);
    }

    public List<User> findAll(){
        return this.repository.findAll();
    }

    public User findByID(Long id) {
        return this.repository.findById(id).orElseThrow(() ->
            new EntidadeNaoEncontradaExeception("User", id));
    }

    @Transactional
    public void delete(Long id){
        User user = this.findByID(id);
        user.setHabilitado(Boolean.FALSE);
        this.repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    return this.repository.findByUsername(username);
    }

    @Transactional
    public User findByUsername(String username) {
	    return repository.findByUsername(username);
    }

}
