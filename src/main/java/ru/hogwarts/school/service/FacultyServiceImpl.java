package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import java.util.List;
import java.util.Optional;


@Service
public class FacultyServiceImpl implements FacultyService {
//    private final Map<Long, Faculty> mapOfFacultys = new HashMap<>();
//    private long id;
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
//        if (mapOfFacultys.containsValue(faculty)) {
//
//        }
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("Ошибка операции!" +
                    " (добавлен ранее)");
        }
//        faculty.setId(++id);
//        mapOfFacultys.put(faculty.getId(), faculty);

        return facultyRepository.save(faculty) ;
    }

    @Override
    public Faculty findFaculty(long id) {
//        if (!mapOfFacultys.containsKey(id)) {
//
//        }
        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден)");
        }

        return faculty.get();
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
//        if (!mapOfFacultys.containsKey(faculty.getId())) {
//
//        }
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден)");
        }
//        mapOfFacultys.put(faculty.getId(), faculty);
        return facultyRepository.save(faculty);
    }


    @Override
    public Faculty deleteFaculty(long id) {
//        Faculty faculty = mapOfFacultys.remove(id);
//        if (faculty == null) {
//
//        }
        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден) ");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }

    @Override
    public List<Faculty> findFacultyWithColor(String color) {
        return facultyRepository.findByColor(color);
    }

}
