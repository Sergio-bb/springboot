package org.sergiob.msvccourses.controllers;

import feign.FeignException;
import org.sergiob.msvccourses.models.User;
import org.sergiob.msvccourses.models.entities.Course;

import org.sergiob.msvccourses.services.contract.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CourseController {

    @Autowired()
    private ICourseService _service;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> add(@Valid @RequestBody Course course, BindingResult result){
        if(result.hasErrors()){
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(_service.add(course));
    }

    @GetMapping("/getall")
    public List<Course> getAll(){
        return _service.getAll();
    }
    @GetMapping("get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        if(id!= null){
            Optional<Course> optional = _service.getById(id);
            if (optional.isPresent()){
                return ResponseEntity.ok(optional.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result,  @PathVariable Long id){
        if(result.hasErrors()){
            return validate(result);
        }
        Optional<Course> optional = _service.getById(id);
        if(optional.isPresent()){
            Course courseDb = optional.get();
            courseDb.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(_service.add(courseDb));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Course> courseDb = _service.getById(id);
        if (courseDb.isPresent()){
            _service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("asingnStudent/{courseId}")
    public ResponseEntity<?> asingnUser(@RequestBody User user,@PathVariable Long courseId){
        Optional<User> optionalUser;
        try {
            optionalUser =  _service.asingnUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body((Collections.singletonMap("message", "user not exist or has error" +
                            " there is an error in communication. ")));
        }

        if(optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("createUser/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user,@PathVariable Long courseId){
        Optional<User> optionalUser;
        try {
            optionalUser =  _service.createUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body((Collections.singletonMap("message", "Error creating user or" +
                            " there is an error in communication. ")));
        }

        if(optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("unasingnStudent/{courseId}")
    public ResponseEntity<?> unasingnStudent(@RequestBody User user,@PathVariable Long courseId){
        Optional<User> optionalUser;
        try {
            optionalUser =  _service.unasingnUser(user, courseId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body((Collections.singletonMap("message", "user not exist or" +
                            " there is an error in communication. ")));
        }

        if(optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }
    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            errors.put(error.getField(), String.format("el campo %s %s", error.getField(),error.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
