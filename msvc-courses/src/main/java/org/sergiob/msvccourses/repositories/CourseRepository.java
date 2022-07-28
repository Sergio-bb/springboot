package org.sergiob.msvccourses.repositories;

import org.sergiob.msvccourses.models.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
