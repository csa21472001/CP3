package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;


    public FacultyServiceImpl(FacultyRepository facultyRepository
            , StudentRepository studentRepository) {
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
        logger.info("Метод addFaculty вернул данные " + savedFaculty);

        return savedFaculty;
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
        logger.info(" Метод findFaculty вернул данные " + findFaculty);

        return findFaculty;
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {

        logger.info("Был вызван метод editFaculty с данными факультета " + faculty);
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден факультет с таким айди)");
        }
        Faculty editFaculty = facultyRepository.save(faculty);
        logger.info(" Метод editFaculty вернул данные " + editFaculty);

        return editFaculty;
    }

    @Override
    public Faculty deleteFaculty(long id) {

        logger.info("Был вызван метод deleteFaculty с данными id = " + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден) ");
        }

        Faculty deleteFaculty = faculty.get();
        logger.info(" Метод deleteFaculty вернул данные " + deleteFaculty);
        facultyRepository.deleteById(id);

        return deleteFaculty;
    }

    @Override
    public Optional<Faculty> findFacultyWithColor(String color) {
        logger.info("Был вызван метод findFacultyWithColor");
        Optional<Faculty> faculty = facultyRepository.findByColor(color);
        logger.info("Из метода findFacultyWithColor возвращен факультет " + faculty);
        return faculty;
    }

    @Override
    public List<Student> findStudentsByFcltId(long id) {

        logger.info("Был вызван метод findStudentByFcltId " + id);

        if (facultyRepository.findById(id).isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден факультет с таким айди)");
        }
        List<Student> students = studentRepository.findByFaculty_id(id);

        logger.info("Из метода findStudentByFcltId вернули cписок студентов " + students);

        return students;
    }

    @Override
    public List<Faculty> findFacultyByString(String color, String name) {

        logger.info("Был вызван метод findFacultyByString и данными " + name + " и " + color);

        if (facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name).isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден факультет с таким айди)");
        }

        List<Faculty> faculties = facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
        logger.info("Из метода findStudentByFcltId вернули cписок студентов " + faculties);

        return faculties;

    }

    @Override
    public List<Faculty> findAll() {
        logger.info("Был вызван метод findAll");
        List<Faculty> faculties = facultyRepository.findAll();
        logger.info(" Из метода findAll возвращен список факультетов в количестве " + faculties);
        return faculties;
    }

//    @Override
//    public String findLongestFcltName() {
//        return facultyRepository.findAll().stream()
//                .map(faculty -> faculty.getName())
//                .max(Comparator.comparingInt(name ->name.length()))
//                .orElseThrow(() -> new FacultyException("No faculty Name"));
//    }

}
