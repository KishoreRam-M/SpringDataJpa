package Restart.MappingTheory.service;

import Restart.MappingTheory.model.Passport;
import Restart.MappingTheory.repo.PassportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassportService {
    @Autowired
    PassportRepo repo;
    public Passport toGetPassport(Passport passport)
    {
        return repo.save(passport);
    }
}
