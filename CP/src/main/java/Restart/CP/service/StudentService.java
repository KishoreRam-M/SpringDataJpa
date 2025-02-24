package Restart.CP.service;

import Restart.CP.model.Student;
import Restart.CP.repo.StudentRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepo repo;

    @PersistenceContext
    private EntityManager entityManager;

    // Method to insert students using batch processing
    @Transactional
    public void batchInsertStudents() {
        List<Student> students = List.of(
                new Student("Alice", "CSE"),
                new Student("Bob", "ECE"),
                new Student("Charlie", "MECH"),
                new Student("David", "CIVIL"),
                new Student("Eve", "IT"),
                new Student("Frank", "EEE"),
                new Student("Grace", "AERO"),
                new Student("Hank", "BIO"),
                new Student("Ivy", "CHEM"),
                new Student("Jack", "AUTO")
        );

        for (int i = 0; i < students.size(); i++) {
            entityManager.persist(students.get(i));

            if (i % 5 == 0) { // Flush & clear every 5 records
                entityManager.flush();  // Execute INSERTs immediately
                entityManager.clear();  // Detach entities to free memory
            }
        }
        System.out.println("✅ Batch Insert Completed!");
    }

    // Fetch all students from DB
    public List<Student> getAllStudents() {
        return repo.findAll();
    }
}
