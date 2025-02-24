package Restart.CP.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    private String name;
    private String dep;

    // Constructor for easy object creation
    public Student(String name, String dep) {
        this.name = name;
        this.dep = dep;
    }
}
