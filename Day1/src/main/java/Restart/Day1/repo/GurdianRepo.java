package Restart.Day1.repo;

import Restart.Day1.model.Guardian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GurdianRepo extends JpaRepository<Guardian,Integer> {
    @Query("SELECT g.email FROM Guardian g")
    List<String> findAllEmails();
}
