package ru.hogwarts.school.service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(long id);

    Student editStudent(Student student);

    Student deleteStudent(long id);

    List<Student> findStudentWithAge(int age);

    Faculty findByNameAndAge(String name, int age);

    List<Student> findByAgeBetween(int min, int max);
}
