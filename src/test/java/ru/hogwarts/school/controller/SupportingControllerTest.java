package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
//import ru.hogwarts.school.repository.StudentRepository;
//import ru.hogwarts.school.service.FacultyServiceImpl;
//import ru.hogwarts.school.service.ThreadServiceImpl;
//
//import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(controllers = {SupportingController.class})
class SupportingControllerTest {
    @Autowired
    MockMvc mockMvc;
//    @SpyBean
//    ThreadServiceImpl threadService;
//    @Autowired
//    SupportingController supportingController;
//    @Autowired
//    StudentRepository studentRepository;

    @Test
    void sumNumbers__returnSum() throws Exception {
        mockMvc.perform(get("/support")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(500000500000L));
    }
}