package Restart.CP.controller;

import Restart.CP.model.Student;
import Restart.CP.service.StudentService;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    @Autowired
    StudentService service;
    @PostMapping("/add")
    public Student toAdd(@RequestBody Student student)
    {
        return service.addStudent(student);
    }
    @GetMapping("/Show")
    public List< Student> toDisplay()
    {
        return   service.BatchProcessing();
    }
}
