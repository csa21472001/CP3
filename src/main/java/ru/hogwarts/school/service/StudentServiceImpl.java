package ru.hogwarts.school.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;
import java.util.Optional;


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

        logger.info("Из метод addStudent вернули" +  studentRepository.save(student));
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

        logger.info(" Метод findStudent вернул данные " + student.get());

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
        logger.info("Метод editStudent вернул новые сохраненные данные " + studentRepository.save(student));

        return studentToFind;
    }


    @Override
    public Student deleteStudent(long id) {

        logger.info("Был вызван метод deleteStudent с данными id = " + id);

        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentException("Ошибка операции!" +
                    "(не найден) ");
        }

        studentRepository.deleteById(id);

        Student studentToDelete = student.get();
        logger.info("Из метода deleteStudent вернули " + student.get());

        return studentToDelete;
    }

    @Override
    public List<Student> findStudentWithAge(int age) {
        List<Student> students = studentRepository.findByAge(age);
        logger.info("Был вызван метод findStudentWithAge c данными: age = " + age + ". Возвращается список студентов " +  studentRepository.findByAge(age));
        return students;
    }

    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        List<Student> students = studentRepository.findByAgeBetween(min, max);
        logger.info("Был вызван метод  findByAgeBetween и возвращен cписок студентов с данными min.age & max.age " + min + " и " + max
                + ". Возвращается список студентов " + studentRepository.findByAgeBetween(min, max));
        return students;
    }
    @Override
    public List<Student> findAll() {
        List<Student> students = studentRepository.findAll();
        logger.info("Был вызван метод findAll и возвращен список студентов " + studentRepository.findAll());
        return students;
    }

    @Override
    public Integer findStudentCount() {
        Integer students = studentRepository.findStudentCount();
        logger.info("Был вызван метод findStudentCount и возвращено количество студентов " + studentRepository.findStudentCount());
        return students;
    }
    @Override
    public Integer findAvgAge() {
        Integer avgAge = studentRepository.findAvgAge();
        logger.info("Был вызван метод findAvgAge и возвращен средний возраст " + studentRepository.findAvgAge());
        return avgAge;
    }
    @Override
    public List<Student> findLastStudents() {
        List<Student> students = studentRepository.findLastStudents(3);
        logger.info("Был вызван метод findLastStudents и возвращен список студентов " + studentRepository.findLastStudents(3) );
        return students;
    }

}
