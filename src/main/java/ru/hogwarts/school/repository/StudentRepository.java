package ru.hogwarts.school.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNameAndAge(String name, int age);
    List<Student> findByAge(int age);
//    List<Student> findAll();
    List<Student> findByAgeBetween(int min, int max);
    List<Student> findByFaculty_id(long faculty_id);
    List<Student> findAll();

}