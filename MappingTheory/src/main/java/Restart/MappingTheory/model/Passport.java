package Restart.MappingTheory.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Passport {
    private  Str name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int passid;
    @OneToOne(mappedBy = "passport",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Person person;
}
