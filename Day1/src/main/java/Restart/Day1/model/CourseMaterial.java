package Restart.Day1.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String url;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public void save(CourseMaterial material) {
    }
}
