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
    //    private final String path = "http://localhost:" + port + "/student";
    Student harry = new Student(1L, "Harry", 10);
    Student harryJr = new Student(1L, "Harry", 9);
    Student ron = new Student(2L, "Ron", 12);
//    Student jorik = new Student(3L,"Jorik",47);
//    Collection<Student> students;

    //    @BeforeEach
//    void beforeEach() {students = List.of(harry,harryJr, ron, jorik);}
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
                        ,String.class );
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
        studentRepository.save(harryJr);
        ResponseEntity<Student> studentResponseEntity = restTemplate
                .exchange("http://localhost:" + port + "/student",
                        HttpMethod.DELETE,
                        new HttpEntity<>(harry),
                        Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(harry.getName(), studentResponseEntity.getBody().getName());
        assertEquals(harry.getAge(), studentResponseEntity.getBody().getAge());
    }

    @Test
    void deleteStudent__return200AndStudent() {
        studentRepository.save(harry);
        ResponseEntity<Student> studentResponseEntity = restTemplate
                .exchange("http://localhost:" + port + "/student",
                        HttpMethod.PUT,
                        new HttpEntity<>(harry),
                        Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(harry.getAge(), studentResponseEntity.getBody().getAge());
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


}