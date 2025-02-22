package Restart.MappingTheory.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded  // ✅ Embed the `Str` class here
    private Str name;

    @OneToOne
    @JoinColumn(name = "person_passport")
    private Passport passport;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Laptop> laptops;
}
