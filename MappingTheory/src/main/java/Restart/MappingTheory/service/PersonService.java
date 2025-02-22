package Restart.MappingTheory.service;

import Restart.MappingTheory.model.Person;
import Restart.MappingTheory.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    PersonRepo repo;
    public Person toGetDetails(Person person)
    {
        return  repo.save(person);
    }
}
