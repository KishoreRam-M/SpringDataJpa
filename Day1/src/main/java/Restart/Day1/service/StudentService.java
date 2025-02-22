package Restart.Day1.service;

import Restart.Day1.model.Student;
import Restart.Day1.repo.StudentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StudentService {
    @Autowired
    StudentRepo repo;

    public String getHello() {
        return "Hello From  KRM....";
    }

    public int getint() {
        return 1 / 0;
    }

    public List<Student> togetStudentList() {
        return repo.findAll();
    }

}