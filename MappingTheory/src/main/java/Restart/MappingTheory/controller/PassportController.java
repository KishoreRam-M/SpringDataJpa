package Restart.MappingTheory.controller;

import Restart.MappingTheory.model.Passport;
import Restart.MappingTheory.repo.PassportRepo;
import Restart.MappingTheory.service.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassportController {
    @Autowired
    PassportService service;
    @PostMapping("/passport")
    public Passport toGetPass(@RequestBody Passport passport)
    {
        System.out.println("I LOVE YOUU....");
        return  service.toGetPassport(passport);
    }

}
