package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> mapOfFaculty = new HashMap<>();
    private long lastId;

    @Override
    public Faculty addFaculty(Faculty faculty) {
        if (mapOfFaculty.containsValue(faculty)) {
            throw new FacultyException("Ошибка операции!" +
                    " (добавлен ранее)");
        }
        faculty.setId(++lastId);
        mapOfFaculty.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty findFaculty(long id) {
        if (!mapOfFaculty.containsKey(id)) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден)");
        }
        return mapOfFaculty.get(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        if (!mapOfFaculty.containsKey(faculty.getId())) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден)");
        }
        mapOfFaculty.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(long id) {
        Faculty faculty = mapOfFaculty.remove(id);
        if (faculty == null) {
            throw new FacultyException("Ошибка операции!" +
                    " (не найден) ");
        }
        return faculty;
    }

    @Override
    public List<Faculty> findFacultyWithColor(String color) {
        return mapOfFaculty.values()
                .stream()
                .filter(entry -> entry.getColor().equals(color))
                .toList();
    }

}
