package Restart.Day1.controller;

import Restart.Day1.repo.GurdianRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gurdian")
public class GurdianController {
    @Autowired
    GurdianRepo repo;
    @GetMapping("/email")
    public List<String> toGetEmail()
    {
        return repo.findAllEmails();
    }
}
