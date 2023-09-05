package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> mapOfStudents = new HashMap<>();
    private long id;
    @Override
    public Student addStudent(Student student) {
        if (mapOfStudents.containsValue(student)) {
            throw new StudentException("Ошибка добавления!" +
                    " (добавлен ранее, не найден)");
        }
        student.setId(++id);
        mapOfStudents.put(student.getId(), student);
        return student;
    }
    @Override
    public Student findStudent(long id) {
        if (!mapOfStudents.containsKey(id)) {
            throw new StudentException("Ошибка добавления!" +
                    " (добавлен ранее, не найден,");
        }
        return mapOfStudents.get(id);
    }
    @Override
    public Student editStudent(Student student) {
        if (!mapOfStudents.containsKey(student.getId())) {
            throw new StudentException("Ошибка добавления!" +
                    " (добавлен ранее, не найден)");
        }
        mapOfStudents.put(student.getId(), student);
        return student;
    }
    @Override
    public Student deleteStudent(long id) {
        Student student = mapOfStudents.remove(id);
        if (student == null) {
            throw new StudentException("Ошибка добавления!" +
                    " (добавлен ранее, не найден) ");
        }
        return student;
    }
    @Override
    public List<Student> findStudentAge(int age) {
        return mapOfStudents.values()
                .stream()
                .filter(entry -> entry.getAge() == age)
                .toList();
    }


}
