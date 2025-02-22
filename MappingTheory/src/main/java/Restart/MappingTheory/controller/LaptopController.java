package Restart.MappingTheory.controller;

import Restart.MappingTheory.model.Laptop;
import Restart.MappingTheory.model.Person;
import Restart.MappingTheory.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LaptopController {
    @Autowired
    LaptopService service;
    @PostMapping("/laptop")
    public Laptop toGetDetail(@RequestBody Laptop lap)
    { System.out.println("I LOVE YOUUU....");
        return service.toGetLap(lap);
    }
}
