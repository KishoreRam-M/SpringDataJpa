package Restart.Day1.repo;

import Restart.Day1.model.Str;
import Restart.Day1.model.Student;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

 import org.springframework.data.domain.Pageable ;

@Repository

public interface StudentRepo extends JpaRepository<Student,Integer> {
    @Query("SELECT s From Student  s where s.name = :name ")
    public Student findByName(@Param("name") String name);

    @Query("SELECT s From Student s WHERE s.email=:email")
    public Student findByEmail(@Param("email") String email);
}



