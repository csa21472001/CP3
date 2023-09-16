package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {FacultyController.class})
public class FacultyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @SpyBean
    FacultyServiceImpl facultyService;
    @Autowired
    FacultyController facultyController;
    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    StudentRepository studentRepository;

    Faculty faculty = new Faculty(1L, "Gryffindor", "red");
    Faculty faculty1 = new Faculty(1L, "Puffendor", "yellow");
    Student student = new Student(1L, "Harry Potter",45 );

    @Test
    void addFaculty__status200AndSavedToDB() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                .content(objectMapper.writeValueAsString(faculty))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }
    @Test
    void findByColor__status200AndReturnFaculty() throws Exception {
        when(facultyRepository.findByColor(faculty.getColor())).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculty/color/" + faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }
    @Test
    void findAll__status200AndReturnListOfFaculties() throws Exception {
        when(facultyRepository.findAll()).thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculty/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()));
    }

    @Test
    void findFacultyById__() throws Exception {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()))
                .andExpect(jsonPath("$.id").value(faculty.getId()));
    }
    @Test
    void editFaculty__status200AndEditedAtDB() throws Exception {
        when(facultyRepository.save(faculty1)).thenReturn(faculty1);
        when(facultyRepository.findById(faculty1.getId())).thenReturn(Optional.of(faculty1));

        mockMvc.perform(put ("/faculty")
                        .content(objectMapper.writeValueAsString(faculty1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty1.getName()))
                .andExpect(jsonPath("$.color").value(faculty1.getColor()));
    }
    @Test
    void deleteFaculty__status200AndDeletedAtDB() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));

        mockMvc.perform(delete ("/faculty/" + faculty.getId())
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }
    @Test
    void findStudentsById__status200AndReturnListOfStudents() throws Exception {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        when(studentRepository.findByFaculty_id(faculty.getId())).thenReturn(List.of(student));

        mockMvc.perform(get("/faculty/studentsById/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
    }
    @Test
    void findFacultyByNameAndColor__status200AndReturnListOfStudents() throws Exception {
        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(faculty.getColor(),faculty.getName()))
                .thenReturn(List.of(faculty));
        mockMvc.perform(get("/faculty/nameAndColor/" + faculty.getColor()
                        + "/" + faculty.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()));
    }


}