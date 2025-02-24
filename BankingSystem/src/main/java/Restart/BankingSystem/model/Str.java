package Restart.BankingSystem.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Str {
    private  String Firstname;

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    private String Lastname;
}
