package ru.hogwarts.school.service;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    public StudentServiceImpl(StudentRepository studentRepository
            ,FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
//        if (mapOfStudents.containsValue(student)) {
//
//        }
        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("Ошибка операции!" +
                    "(добавлен ранее)");
        }
//        student.setId(++id);
//        mapOfStudents.put(student.getId(), student);

        return studentRepository.save(student) ;
    }

    @Override
    public Student findStudent(long id) {
//        if (!mapOfStudents.containsKey(id)) {
//
//        }
        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentException("Ошибка операции!" +
                    "(не найден)");
        }

        return student.get();
    }

    @Override
    public Student editStudent(Student student) {
//        if (!mapOfStudents.containsKey(student.getId())) {
//
//        }
        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("Ошибка операции!" +
                    "(не найден)");
        }
//        mapOfStudents.put(student.getId(), student);
        return studentRepository.save(student);
    }


    @Override
    public Student deleteStudent(long id) {
//        Student student = mapOfStudents.remove(id);
//        if (student == null) {}

        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentException("Ошибка операции!" +
                    "(не найден) ");
        }
        studentRepository.deleteById(id);
        return student.get();
    }

    @Override
    public List<Student> findStudentWithAge(int age) {
        return studentRepository.findByAge(age);
    }

    @Override
    public Faculty findByNameAndAge(String name, int age) {

        if (studentRepository.findByNameAndAge(name, age).isEmpty()) {
            throw new StudentException("Ошибка операции!" +
                    "(не найден) ");
        }
        return  studentRepository.findByNameAndAge(name, age).get().getFaculty();
    }
    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

}
