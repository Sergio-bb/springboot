package org.sergiob.msvccourses.controllers;

import org.sergiob.msvccourses.models.entities.Course;

import org.sergiob.msvccourses.services.contract.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            errors.put(error.getField(), String.format("el campo %s %s", error.getField(),error.getDefaultMessage()));
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
