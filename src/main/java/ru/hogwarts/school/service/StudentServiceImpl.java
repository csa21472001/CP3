package ru.hogwarts.school.service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    public StudentServiceImpl(StudentRepository studentRepository
            ,FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {

        logger.info("Был вызван метод addStudent с данными " + student);
        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("Ошибка операции!" +
                    "(добавлен ранее)");
        }
        Student savedStudent = studentRepository.save(student);
        logger.info("Из метод addStudent вернули" +  savedStudent);

        return savedStudent ;
    }

    @Override
    public Student findStudent(long id) {

        logger.info("Был вызван метод findStudent с данными id = " + id);

        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("Ошибка операции!" +
                    "(не найден)");
        }

        Student studentToFind = student.get();
        logger.info(" Метод findStudent вернул данные " + studentToFind);

        return studentToFind;
    }

    @Override
    public Student editStudent(Student student) {

        logger.info("Был вызван метод editStudent с данными " + student);

        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("Ошибка операции!" +
                    "(не найден)");
        }
        Student studentToFind = studentRepository.save(student);
        logger.info("Метод editStudent вернул новые сохраненные данные " + studentToFind);

        return studentToFind;
    }


    @Override
    public Student deleteStudent(long id) {

        logger.info(" Был вызван метод deleteStudent с данными id = " + id);

        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentException("Ошибка операции!" +
                    "(не найден) ");
        }

        studentRepository.deleteById(id);

        Student studentToDelete = student.get();
        logger.info(" Из метода deleteStudent вернули " + studentToDelete);

        return studentToDelete;
    }

    @Override
    public List<Student> findStudentWithAge(int age) {
        logger.info(" Был вызван метод findStudentWithAge c возрастом " + age );
        List<Student> students = studentRepository.findByAge(age);
        logger.info(" Из метода findStudentWithAge возвращается список студентов " +  students);
        return students;
    }

    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Метод findByAgeBetween вызван. Возвращен список студентов с возрастом между" + min + " и " + max);
        List<Student> students = studentRepository.findByAgeBetween(min, max);
        logger.info("Из метода findByAgeBetween возвращен cписок студентов " + students);
        return students;
    }
    @Override
    public List<Student> findAll() {
        logger.info("Был вызван метод findAll");
        List<Student> students = studentRepository.findAll();
        logger.info("Из метода findAll возвращен список студентов " + students);
        return students;
    }

    @Override
    public Integer findStudentCount() {
        logger.info("Был вызван метод findStudentCount");
        Integer students = studentRepository.findStudentCount();
        logger.info("Из метода findStudentCount возвращено количество студентов " + students);
        return students;
    }
    @Override
    public Integer findAvgAge() {
        logger.info("Был вызван метод findAvgAge");
        Integer avgAge = studentRepository.findAvgAge();
        logger.info("Из метода findAvgAge возвращен средний возраст " + avgAge);
        return avgAge;
    }
    @Override
    public List<Student> findLastStudents() {
        logger.info("Был вызван метод findLastStudents");
        List<Student> students = studentRepository.findLastStudents(3);
        logger.info("Из метода findLastStudents возвращен список студентов " + students);
        return students;
    }

//    @Override
//    public List<String> findNameWithFirstLetterIsA() {
//        return studentRepository.findAll().stream()
//                .map(stud -> stud.getName())
//                .filter(name -> StringUtils.startsWithIgnoreCase(name,"a"))
//                .map(name -> name.toUpperCase())
//                .sorted()
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public Double findAvgAgeByStream() {
//        return studentRepository.findAll().stream()
//                .mapToInt(student -> student.getAge())
//                .average()
//                .orElse(0);
//    }

}
