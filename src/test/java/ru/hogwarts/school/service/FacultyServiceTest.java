package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    //    Faculty faculty = new Faculty(1L, "Slytherin", "green");
    @InjectMocks
    FacultyServiceImpl underTest;
    @Mock
    FacultyRepository facultyRepository;
    @Mock
    StudentRepository studentRepository;
    Faculty faculty = new Faculty(4L, "Puffendor", "yellow");
    Faculty facultyG = new Faculty(1L, "Griffindor", "red");
    Student student = new Student(1L, "Harry", 10);

    @BeforeEach
    void beforeAll() {
        student.setFaculty(faculty);
    }

    @Test
    void addFaculty_newFaculty_facultyAdded() {
        when(facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()))
                .thenReturn(Optional.empty());
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty facultyCheck = underTest.addFaculty(faculty);
        assertEquals(faculty, facultyCheck);
    }

    @Test
    void addFaculty_repeatedFacultyValue_throwException() {
        when(facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()))
                .thenReturn(Optional.of(faculty));
//        underTest.addFaculty(faculty);
        assertThrows(FacultyException.class,
                () -> underTest.addFaculty(faculty));
    }

    @Test
    void findFaculty_facultyToFind_returnFoundFaculty() {
        when(facultyRepository.findById(4L)).thenReturn(Optional.of(faculty));
        Faculty facultyCheck = underTest.findFaculty(4);
        assertEquals(faculty, facultyCheck);
    }

    @Test
    void findFaculty_facultyThatCannotBeFound_throwException() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(FacultyException.class,
                () -> underTest.findFaculty(1L));
    }

    @Test
    void editFaculty_updateFaculty_facultyUpdated() {
//        underTest.addFaculty(faculty);
        Faculty updFaculty = new Faculty(1L, "Griffindor", "red");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(updFaculty));
        when(facultyRepository.save(updFaculty)).thenReturn(updFaculty);
        Faculty updFacultyCheck = underTest.editFaculty(updFaculty);
        assertEquals(updFaculty, updFacultyCheck);
    }

    @Test
    void editFaculty_repeatedUpdatedFaculty_throwException() {
        when(facultyRepository.findById(4L)).thenReturn(Optional.empty());
        assertThrows(FacultyException.class,
                () -> underTest.editFaculty(faculty));
    }

    @Test
    void deleteFaculty_repeatedRemovalOfTheFaculty_throwException() {
        when(facultyRepository.findById(4L)).thenReturn(Optional.empty());
        assertThrows(FacultyException.class,
                () -> underTest.deleteFaculty(4));
    }

    @Test
    void deleteFaculty_facultyToDelete_facultyRemoved() {
        when(facultyRepository.findById(4L)).thenReturn(Optional.of(faculty));
        doNothing().when(facultyRepository).deleteById(4L);
        Faculty updFaculty = underTest.deleteFaculty(4L);
        assertEquals(faculty, updFaculty);
    }

    @Test
    void findFacultyWithColor_сertainColor_listOfFacultyWithCertainColor() {
        when(facultyRepository.findByColor(faculty.getColor())).thenReturn(Optional.of(faculty));
        assertEquals(Optional.of(faculty), underTest.findFacultyWithColor(faculty.getColor()));
    }

    @Test
    void findStudentsByFcltId_wrongId_throwException() {
        when(facultyRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(FacultyException.class,
                () -> underTest.findStudentsByFcltId(2L));
    }

    @Test
    void findStudentsByFcltId_facultyId_listOfStudents() {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        when(studentRepository.findByFaculty_id(faculty.getId())).thenReturn(List.of(student));
        assertEquals(List.of(student), underTest.findStudentsByFcltId(faculty.getId()));
    }
    @Test
    void findFacultyByString_colorAndName_returnFaculty() {
        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(faculty.getColor(),faculty.getName())).thenReturn(List.of(faculty));
        assertEquals(List.of(faculty), underTest.findFacultyByString(faculty.getColor(),faculty.getName()));
    }
    @Test
    void findFacultyByString_wrongColorOrName_throwException() {
        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(faculty.getColor(),faculty.getName())).thenReturn(Collections.emptyList());
        assertThrows(FacultyException.class,
                () -> underTest.findFacultyByString(faculty.getColor(),faculty.getName()));
    }
    @Test
    void findLongestFcltName_noneFclts_throwException() {
        when(facultyRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(FacultyException.class,
                () -> underTest.findLongestFcltName());
    }
    @Test
    void findLongestFcltName_listOfFclt_returnTheLongestName()  {
        when(facultyRepository.findAll()).thenReturn(List.of(faculty,facultyG));
        String fclt = underTest.findLongestFcltName();
        assertEquals(facultyG.getName(),fclt);
    }

}