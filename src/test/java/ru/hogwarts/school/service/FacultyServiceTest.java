package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
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
    Faculty faculty = new Faculty(4L, "Puffendor", "yellow");

    @Test
    void addFaculty_newFaculty_facultyAdded() {
        when(facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()))
                .thenReturn(Optional.empty());
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty facultyCheck = underTest.addFaculty(faculty);
        assertEquals(faculty, facultyCheck);
//        assertTrue(underTest.getAll().contains(faculty));
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
        when(facultyRepository.findByColor("yellow")).thenReturn(List.of(faculty));
        assertEquals(List.of(faculty), underTest.findFacultyWithColor("yellow"));
    }

}