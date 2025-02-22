package Restart.Day1.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Str {
    private String firstname;
    private String middlename;
    private String lastname;
}
