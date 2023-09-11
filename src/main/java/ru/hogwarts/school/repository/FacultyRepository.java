package ru.hogwarts.school.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;
import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByNameAndColor(String name, String color);

    Optional<Faculty> findByColor(String color);

    List<Faculty> findAll();

    Optional<Faculty> findByName(String name);
}
