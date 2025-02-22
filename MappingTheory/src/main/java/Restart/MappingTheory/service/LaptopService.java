package Restart.MappingTheory.service;

import Restart.MappingTheory.model.Laptop;
import Restart.MappingTheory.repo.LaptopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaptopService {
    @Autowired
    LaptopRepo repo;
    public Laptop toGetLap(Laptop laptop)
    {
        return repo.save(laptop);
    }
}
