package Restart.Day1.controller;

import Restart.Day1.model.Student;
import Restart.Day1.repo.StudentRepo;
import Restart.Day1.service.StudentService;
import org.springframework.data.domain.Page ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController  // Ensure this annotation is present on your controller
@RequestMapping("/students") // Base path for all student-related endpoints
public class StudentController {

    @Autowired
    private StudentRepo repo; // Inject StudentRepository
    @Autowired
    private StudentService service;

    @GetMapping("/all")
    public List<Student> getAllStudents() { // Use a more meaningful method name
        return repo.findAll();
    }
    @GetMapping("/emails/{email}")
    public Student getEmail(@PathVariable String email) {
        return repo.findByEmail(email); // Directly return the student object
    }

    @GetMapping("/s")
    public String toget()
    {
        return service.getHello();
    }
    @GetMapping("/ss")
    public  int togetint()
    {
        return  service.getint();
    }
    @GetMapping("/sss")
    public   List <Student> togett()
    {
        return service.togetStudentList();
    }
    @GetMapping("/paging/{i}/{f}")
    public Page<Student> toPaging(@PathVariable int i, @PathVariable int f) {
        // Prevent negative values
        if (i < 0) {
            throw new IllegalArgumentException("Page number cannot be negative.");
        }
        if (f <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0.");
        }

        Pageable pageable = PageRequest.of(i, f);
        return repo.findAll(pageable);
    }
@GetMapping("/sort")
    public List < Student> toGetSort()
{
    return  repo.findAll(Sort.by("id").descending());}

   }


