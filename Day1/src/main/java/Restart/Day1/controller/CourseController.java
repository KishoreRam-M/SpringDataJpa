package Restart.Day1.controller;

import Restart.Day1.model.Course;
import Restart.Day1.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping("/{name}") // ✅ Correctly maps /course/Music
    public Course getCourseByName(@PathVariable String name) {
        System.out.println(name);
        return service.toFindByname(name);
    }
}
