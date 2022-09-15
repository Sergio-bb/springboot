package org.sergiob.mscv.usuarios.mscv.usuarios.controllers;

import org.sergiob.mscv.usuarios.mscv.usuarios.models.entities.User;
import org.sergiob.mscv.usuarios.mscv.usuarios.services.contract.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private IUserService _service;

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody User user, BindingResult result){
        if(result.hasErrors()){
            return validate(result);
        }
        if(_service.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections.
                            singletonMap("message", "the email is used"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(_service.add(user));
    }

    @GetMapping("/getall")
    public List<User> getAll(){
        return _service.getAll();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        if(id!= null){
            Optional<User> optional = _service.getById(id);
            if (optional.isPresent()){
                return ResponseEntity.ok(optional.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user,BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validate(result);
        }

        Optional<User> optionalUser = _service.getById(id);
        if(optionalUser.isPresent()){
            User userDb = optionalUser.get();
            if(!user.getEmail().equalsIgnoreCase(userDb.getEmail()) && _service.findByEmail(user.getEmail()).isPresent()){
                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "the email is used"));
            }
            userDb.setEmail(user.getEmail());
            userDb.setName(user.getName());
            userDb.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(_service.add(userDb));
        }
            return ResponseEntity.notFound().build();

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<User> userDb = _service.getById(id);
        if (userDb.isPresent()){
            _service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findUsersById")
    public ResponseEntity<?> findStudensById(@RequestParam List<Long> ids){
        return ResponseEntity.ok(_service.findByIds(ids));
    }
    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            errors.put(error.getField(), String.format("el campo %s %s", error.getField(),error.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
