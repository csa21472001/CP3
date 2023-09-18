package ru.hogwarts.school.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;


    public FacultyServiceImpl(FacultyRepository facultyRepository
            ,StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {

        logger.info("Был вызван метод addFaculty с данными " + faculty);

        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("Ошибка операции!" +
                    " (добавлен ранее)");
        }
        Faculty savedFaculty = facultyRepository.save(faculty);

        logger.info("Был вызван метод addFaculty с данными " + faculty);

        return savedFaculty ;
    }

    @Override
    public Faculty findFaculty(long id) {

        logger.info("Был вызван метод findFaculty с данными id = " + id);

        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден)");
        }

        Faculty findFaculty = faculty.get();

        logger.info(" Метод findFaculty вернул данные " + faculty.get());

        return findFaculty;
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {

        logger.info("Был вызван метод editFaculty с данными факультета " + faculty);

        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден факультет с таким айди)");
        }
        Faculty findFaculty = facultyRepository.save(faculty);

        logger.info(" Метод editFaculty вернул данные " + faculty);

        return findFaculty;
    }
    @Override
    public Faculty deleteFaculty(long id) {

        logger.info("Был вызван метод deleteFaculty с данными id = " + id);

        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден) ");
        }

        Faculty findFaculty = faculty.get();

        logger.info(" Метод deleteFaculty вернул данные " + faculty.get());

        facultyRepository.deleteById(id);
        return findFaculty;
    }
    @Override
    public Optional<Faculty> findFacultyWithColor(String color) {

        logger.info("Был вызван метод findFacultyWithColor и возвращен факультет с цветом - " + color);
        return facultyRepository.findByColor(color);
    }

    @Override
    public List<Student> findStudentsByFcltId(long id) {

        logger.info("Был вызван метод findStudentByFcltId " + id);

        if (facultyRepository.findById(id).isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден факультет с таким айди)");
        }
        List<Student> students =  studentRepository.findByFaculty_id(id);

        logger.info("Из метода findStudentByFcltId вернули cписок студентов " + studentRepository.findByFaculty_id(id));

        return students;
    }
    @Override
    public List<Faculty> findFacultyByString(String color, String name) {

        logger.info("Был вызван метод findFacultyByString и возвращен факультет с названием и цветом - " + name + " " + color);

        if (facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name).isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден факультет с таким айди)");
        }

        List<Faculty> faculties = facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);

        logger.info("Из метода findStudentByFcltId вернули cписок студентов " + facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name));

        return faculties;

    }

    @Override
    public List<Faculty> findAll() {

        List<Faculty> faculties = facultyRepository.findAll();

        logger.info("Был вызван метод findAll и возвращен список факультетов в количестве " + facultyRepository.findAll().size() + " факультетов." );

        return faculties;
    }

}
