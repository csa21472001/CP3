package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}")
    public Faculty findFaculty(@PathVariable Long id) {
        return facultyService.findFaculty(id);
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty removeFaculty(@PathVariable Long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping("/color/{color}")
    public Optional<Faculty> findFacultyWithColor(@PathVariable String color) {
        return facultyService.findFacultyWithColor(color);
    }

    @GetMapping("/studentsById/{id}")
    public List<Student> findStudentsByFcltId(@PathVariable long id) {
        return facultyService.findStudentsByFcltId(id);
    }

    @GetMapping("/nameAndColor/{color}/{name}")
    public List<Faculty> findFacultyByString(@PathVariable String color, @PathVariable String name) {
        return facultyService.findFacultyByString(color, name);
    }

    @GetMapping("/all")
    public List<Faculty> findAll() {
        return facultyService.findAll();
    }

    @GetMapping("/findLongestName")
    public String findLongestFcltName() {
        return facultyService.findLongestFcltName();
    }
}
