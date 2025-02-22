package Restart.Day1.controller;

import Restart.Day1.model.*;
import Restart.Day1.repo.CourseRepo;
import Restart.Day1.repo.StudentRepo;
import Restart.Day1.repo.TeacherRepo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/material")
public class MaterialController {

    private static final Logger log = Logger.getLogger(MaterialController.class.getName());

    @Autowired
    private CourseRepo courseRepository;

    @Autowired
    private CourseMaterial materialRepository;

    @Autowired
    private StudentRepo studentRepository;

    @Autowired
    private TeacherRepo teacherRepository;

    @Transactional
    public void add() {
        CourseMaterial material = new CourseMaterial();
        material.setId(100);
        material.setUrl("WWW KRM.COM");

        Course course = new Course();
        course.setId(100);
        course.setMaterial(material);
        course.setName("Become a Man");
        course.setCredits(100);

        Student s = new Student();
        Str str = new Str();
        str.setFirstname("Kishore");
        str.setLastname("M");
        str.setMiddlename("Ram");
        s.setName(str);
        s.setId(101);
        s.setEmail("mrkisho65");
        s.setCourses(List.of(course)); // Correct list assignment

        Teacher t = new Teacher();
        t.setName(str);
        t.setId(102);
        t.setCourses(List.of(course));

        course.setStudents(List.of(s)); // Correcting list assignment
        course.setTeacher(t);

        // Save entities in the correct order
        //materialRepository.save(material);
        courseRepository.save(course);
        studentRepository.save(s);
        teacherRepository.save(t);

        log.info("All data added successfully.");

    }
}
