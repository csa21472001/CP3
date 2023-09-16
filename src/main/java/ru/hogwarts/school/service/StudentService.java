package ru.hogwarts.school.service;

import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;
import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(long id);

    Student editStudent(Student student);

    Student deleteStudent(long id);

    List<Student> findStudentWithAge(int age);

//    Faculty findByNameAndAge(String name, int age);

    List<Student> findByAgeBetween(int min, int max);
    List<Student> findAll();

    Integer findStudentCount();
    Integer findAvgAge();
    List<Student> findLastStudents();

}
