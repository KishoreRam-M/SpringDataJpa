package Restart.Day1.service;

import Restart.Day1.model.Course;
import Restart.Day1.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    CourseRepo repo;
    public Course toFindByname(String name )
    {
        return  repo.findByName(name);
    }
}
