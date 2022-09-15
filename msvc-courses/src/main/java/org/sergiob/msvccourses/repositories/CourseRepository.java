package org.sergiob.msvccourses.repositories;

import org.sergiob.msvccourses.models.entities.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
    @Modifying
    @Query("delete from Student st where st.userId=?1")
    void deleteStudentById(Long id);
}
