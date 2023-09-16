package ru.hogwarts.school.service;
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

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;


    public FacultyServiceImpl(FacultyRepository facultyRepository
            ,StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {

        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("Ошибка операции!" +
                    " (добавлен ранее)");
        }

        return facultyRepository.save(faculty) ;
    }

    @Override
    public Faculty findFaculty(long id) {

        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден)");
        }
        return faculty.get();
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден факультет с таким айди)");
        }
        return facultyRepository.save(faculty);
    }
    @Override
    public Faculty deleteFaculty(long id) {

        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден) ");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }
    @Override
    public Optional<Faculty> findFacultyWithColor(String color) {
        return facultyRepository.findByColor(color);
    }

    @Override
    public List<Student> findStudentsByFcltId(long id) {
        if (facultyRepository.findById(id).isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден факультет с таким айди)");
        }
        return studentRepository.findByFaculty_id(id);
    }
    @Override
    public List<Faculty> findFacultyByString(String color, String name) {

        if (facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name).isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден факультет с таким айди)");
        }

        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);

    }

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

}
