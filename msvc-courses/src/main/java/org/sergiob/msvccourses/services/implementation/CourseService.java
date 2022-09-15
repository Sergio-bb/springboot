package org.sergiob.msvccourses.services.implementation;

import org.sergiob.msvccourses.models.User;
import org.sergiob.msvccourses.models.entities.Course;
import org.sergiob.msvccourses.models.entities.Student;
import org.sergiob.msvccourses.repositories.CourseRepository;
import org.sergiob.msvccourses.rest.IUserClient;
import org.sergiob.msvccourses.services.contract.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService implements ICourseService {

    @Autowired
    private CourseRepository _courseRepository;
    @Autowired
    private IUserClient _userClient;
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
    @Transactional()
    public Optional<Course> getByIdWhitUsers(Long id) {
        Optional<Course> optional = _courseRepository.findById(id);
        if(optional.isPresent()){
            Course course = optional.get();
            if(!course.getStudents().isEmpty()){
                List<Long> ids = course.getStudents().stream().map(Student::getUserId).toList();
                List<User> users = _userClient.findUsersByIds(ids);
                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
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

    @Override
    @Transactional
    public void deleteStudentById(Long id) {
        _courseRepository.deleteStudentById(id);
    }

    @Override
    @Transactional
    public Optional<User> asingnUser(User user, Long courseId) {
       Optional<Course> optional = _courseRepository.findById(courseId);
       if(optional.isPresent()){
           User externalUser = _userClient.getUser(user.getId());

           Course course = optional.get();
           Student student = new Student();
           student.setUserId(user.getId());

           course.addStudent(student);
           _courseRepository.save(course);
           return Optional.of(externalUser);
       }else {
           return Optional.empty();
       }
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> optional = _courseRepository.findById(courseId);
        if(optional.isPresent()){
            User newUser = _userClient.create(user);

            Course course = optional.get();
            Student student = new Student();

            student.setUserId(user.getId());
            course.addStudent(student);
            _courseRepository.save(course);
            return Optional.of(newUser);
        }else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<User> unasingnUser(User user, Long courseId) {
        Optional<Course> optional = _courseRepository.findById(courseId);
        if(optional.isPresent()){
            User externalUser = _userClient.getUser(user.getId());

            Course course = optional.get();
            Student student = new Student();
            student.setUserId(user.getId());

            course.removestudent(student);
            _courseRepository.save(course);
            return Optional.of(externalUser);
        }else {
            return Optional.empty();
        }
    }
}
