package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    int port;
    @Autowired
    StudentController studentController;
    @Autowired
    FacultyService facultyService;
    @Autowired
    StudentService studentService;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    StudentRepository studentRepository;

    Student harry = new Student(1L, "aHarry", 10);
    Student harryJr = new Student(1L, "Harry", 9);
    Student ron = new Student(2L, "aRon", 12);

    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();
    }

    @Test
    void addStudent_returnStatus200AndStudent() {
        ResponseEntity<Student> studentResponseEntity = restTemplate
                .postForEntity("http://localhost:" + port + "/student",
                        harry,
                        Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(harry.getName(), studentResponseEntity.getBody().getName());
        assertEquals(harry.getAge(), studentResponseEntity.getBody().getAge());
    }

    @Test
    void findStudent__returnStatus400AndStudent() {
        ResponseEntity<String> studentResponseEntity = restTemplate
                .getForEntity("http://localhost:" + port + "/student/" + harry.getId()
                        , String.class);
        assertEquals(HttpStatus.BAD_REQUEST, studentResponseEntity.getStatusCode());
        assertEquals("Ошибка операции!" + "(не найден)", studentResponseEntity.getBody());
    }

    @Test
    void getAll__returnStatus200AndStudentList() {
        studentRepository.save(harry);
        studentRepository.save(ron);
        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/getAll",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(harry, ron), exchange.getBody());
    }

    @Test
    void editStudent__return200AndStudent() {
        Student student = studentRepository.save(harryJr);
        ResponseEntity<Student> studentResponseEntity = restTemplate
                .exchange("http://localhost:" + port + "/student",
                        HttpMethod.PUT,
                        new HttpEntity<>(student),
                        Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());
    }

    @Test
    void deleteStudent__return200AndStudent() {
        Student student = studentRepository.save(harry);
        ResponseEntity<Student> studentResponseEntity = restTemplate
                .exchange("http://localhost:" + port + "/student/" + student.getId(),
                        HttpMethod.DELETE,
                        null,
                        Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());
    }

    @Test
    void findStudentByAge__return200AndListOfStudents() {
        studentRepository.save(harry);
        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/" + harry.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(harry), exchange.getBody());
    }

    @Test
    void findByAgeBetweenMinAndMax__return200AndListOfStudents() {
        studentRepository.save(harry);
        studentRepository.save(ron);

        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/allAgedBetween?min=10&max=15",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(harry, ron), exchange.getBody());
    }
    @Test
    void findAvgAgeByStream__returnDoubleAvgAge() {
        studentRepository.save(harry);
        studentRepository.save(harryJr);
        studentRepository.save(ron);

        ResponseEntity<Double> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/avgAgeByStream",
                HttpMethod.GET,
                null,
                Double.class);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(harry, ron,harryJr), exchange.getBody());
    }
 @Test
    void findNameWithFirstLetterIsA__returnSortedNamesStartedWithA() {
        studentRepository.save(harry);
        studentRepository.save(ron);

        ResponseEntity<List<String>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/aName",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(harry.getName(),ron.getName()), exchange.getBody());
    }



}