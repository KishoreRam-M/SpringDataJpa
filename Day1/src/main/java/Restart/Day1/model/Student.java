package Restart.Day1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"courses", "guardian"})  // Prevents infinite recursion
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "Student",
        indexes = {
                @Index(name = "idx_student_guardian", columnList = "id, guardian_id")  // Composite Index
        }
)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private Str name;  // Assuming Name is an @Embeddable class

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses;

    @ManyToOne
    @JoinColumn(name = "guardian_id", nullable = false)
    private Guardian guardian;

}
