package org.sergiob.mscv.usuarios.mscv.usuarios.controllers;

import org.sergiob.mscv.usuarios.mscv.usuarios.models.entities.User;
import org.sergiob.mscv.usuarios.mscv.usuarios.services.contract.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private IUserService _service;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@RequestBody User user){
        return _service.add(user);
    }

    @GetMapping("/getall")
    public List<User> getAll(){
        return _service.getAll();
    }
    @GetMapping("get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        if(id!= null){
            Optional<User> optional = _service.getById(id);
            if (optional.isPresent()){
                return ResponseEntity.ok(optional.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id){
        Optional<User> optionalUser = _service.getById(id);
        if(optionalUser.isPresent()){
            User userDb = optionalUser.get();
            userDb.setEmail(user.getEmail());
            userDb.setName(user.getName());
            userDb.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(_service.add(userDb));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<User> userDb = _service.getById(id);
        if (userDb.isPresent()){
            _service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
