package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {SupportingController.class})
class InfoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void findPortNumber__returnIntegerWithPortnumber() throws Exception {

        mockMvc.perform(get("/info/getPort")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.port").value(8081));

    }
}

//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import ru.hogwarts.school.controller.StudentController;
//import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.repository.FacultyRepository;
//import ru.hogwarts.school.repository.StudentRepository;
//import ru.hogwarts.school.service.FacultyService;
//import ru.hogwarts.school.service.StudentService;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class InfoControllerTest {
//
//    @Autowired
//    TestRestTemplate restTemplate;
//    @LocalServerPort
//    int port;
//
//    @Test
//    void findPort() {
//        ResponseEntity<Integer> portResponseEntity = restTemplate
//                .postForEntity("http://localhost:" + port + "/info/getPort",
//                        HttpMethod.GET,
//                        null,
//                        Integer.class);
//        assertEquals(HttpStatus.OK, portResponseEntity.getStatusCode());
//        assertEquals(8081, portResponseEntity.getBody());
//    }