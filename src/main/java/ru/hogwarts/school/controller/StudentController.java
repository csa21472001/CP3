package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;


@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);}

    @GetMapping("/{id}")
    public Student findStudent(@PathVariable Long id) {
        return studentService.findStudent(id);}

    @PutMapping
    public Student editStudent(@RequestBody Student student) {
        return studentService.editStudent(student);}

    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);}

    @GetMapping("/age/{age}")
    public List<Student> findStudentAge(@PathVariable int age) {
        return studentService.findStudentWithAge(age);}

    @GetMapping("/getAll")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/allAgedBetween")
    public List<Student> findByAgeBetween(@RequestParam int min
            , @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }
//    @GetMapping("/faculty/{name}/{age}")
//    public Faculty getFacultyByNameAndAge(@PathVariable String name
//            , @PathVariable int age) {
//        return studentService.findByNameAndAge(name, age);
//    }
}
