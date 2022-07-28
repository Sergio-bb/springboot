package org.sergiob.msvccourses.services.contract;

import org.sergiob.msvccourses.models.entities.Course;
import org.sergiob.msvccourses.services.implementation.CourseService;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    List<Course> getAll();
    Optional<Course> getById(Long id);
    Course add(Course course);
    void delete(Long id);
}
