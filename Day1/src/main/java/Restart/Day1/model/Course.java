package Restart.Day1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int credits;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;

    @OneToOne(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CourseMaterial material;

    @ManyToOne
    @JoinColumn(name = "teacher_id") // FK for Teacher
    private Teacher teacher;
}
