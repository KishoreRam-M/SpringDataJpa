package Restart.MappingTheory.controller;

import Restart.MappingTheory.model.Person;
import Restart.MappingTheory.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @Autowired
    PersonService service;
    @PostMapping("/person")
    public Person toGetPerson(@RequestBody Person person)
    { System.out.println("I LOVE YOOUU....");
        return service.toGetDetails(person);
    }
}
