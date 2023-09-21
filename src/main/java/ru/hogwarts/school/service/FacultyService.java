package ru.hogwarts.school.service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);
    Faculty findFaculty(long id);
    Faculty editFaculty(Faculty faculty);
    Faculty deleteFaculty(long id);
    Optional<Faculty> findFacultyWithColor(String color);
    List<Student> findStudentsByFcltId(long id);
    List<Faculty> findFacultyByString(String color, String name);
    List<Faculty> findAll();

//    String findLongestFcltName();
}
