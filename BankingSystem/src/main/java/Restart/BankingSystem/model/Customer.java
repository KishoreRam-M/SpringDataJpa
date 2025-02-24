package Restart.BankingSystem.model;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Embedded
    private  Str name;
    @Id
    private  long account_id;

}
