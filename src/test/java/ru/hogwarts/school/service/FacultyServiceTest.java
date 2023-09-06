package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {
    private Faculty faculty = new Faculty(1L, "Slytherin", "green");
    FacultyServiceImpl underTest = new FacultyServiceImpl();


    @Test
    void addFaculty_newFaculty_facultyAdded() {
        Faculty facultyCheck = underTest.addFaculty(faculty);
        assertEquals(faculty, facultyCheck);
    }

    @Test
    void addFaculty_repeatedFacultyValue_throwException() {
        underTest.addFaculty(faculty);
        assertThrows(FacultyException.class,
                () -> underTest.addFaculty(faculty));
    }

    @Test
    void findFaculty_facultyToFind_returnFoundFaculty() {
        underTest.addFaculty(faculty);
        assertEquals(faculty, underTest.findFaculty(1));
    }

    @Test
    void findFaculty_facultyThatCannotBeFound_throwException() {
        assertThrows(FacultyException.class,
                () -> underTest.findFaculty(1));
    }

    @Test
    void editFaculty_updateFaculty_facultyUpdated() {
        underTest.addFaculty(faculty);
        Faculty updFaculty = new Faculty(1L, "Griffindor", "white");
        Faculty updFacultyCheck = underTest.editFaculty(updFaculty);
        assertEquals(updFaculty, updFacultyCheck);
    }

    @Test
    void editFaculty_repeatedUpdatedFaculty_throwException() {
        underTest.addFaculty(faculty);
        assertThrows(FacultyException.class,
                () -> underTest.editFaculty(faculty));
    }

    @Test
    void deleteFaculty_repeatedRemovalOfTheFaculty_throwException() {
        underTest.addFaculty(faculty);
        underTest.deleteFaculty(1);
        assertThrows(FacultyException.class,
                () -> underTest.deleteFaculty(1));
    }

    @Test
    void deleteFaculty_facultyForDelete_facultyRemoved() {
        underTest.addFaculty(faculty);
        Faculty updFaculty = underTest.deleteFaculty(1);
        assertEquals(faculty, updFaculty);
    }

    @Test
    void findFacultyWithColor_сertainColor_listOfFacultyWithCertainColor() {
        underTest.addFaculty(faculty);
        assertEquals(List.of(faculty), underTest.findFacultyWithColor("green"));
    }
}