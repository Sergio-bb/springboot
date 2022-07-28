package org.sergiob.msvccourses.services.implementation;

import org.sergiob.msvccourses.models.entities.Course;
import org.sergiob.msvccourses.repositories.CourseRepository;
import org.sergiob.msvccourses.services.contract.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private CourseRepository _courseRepository;
    @Override
    @Transactional
    public List<Course> getAll() {
       return (List<Course>) _courseRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Course> getById(Long id) {
        return _courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course add(Course course) {
        return _courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        _courseRepository.deleteById(id);
    }
}
