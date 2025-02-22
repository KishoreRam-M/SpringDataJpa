package Restart.MappingTheory.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;
    private String model;
    private String price;

    @ManyToOne
    @JoinColumn(name = "person_id") // ✅ Correctly references the `id` in Person table
    private Person person;
}
