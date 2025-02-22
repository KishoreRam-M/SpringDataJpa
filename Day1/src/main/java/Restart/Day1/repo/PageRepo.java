package Restart.Day1.repo;

import Restart.Day1.model.Student;
 import org.springframework.data.domain.Page ;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
@EnableJpaRepositories
public interface PageRepo extends PagingAndSortingRepository<Student,Integer> {
    @Query("SELECT s FROM Student s WHERE s.name = :name")
    Page <Student> findByName(@Param("name") String name, Pageable pageable);
}

