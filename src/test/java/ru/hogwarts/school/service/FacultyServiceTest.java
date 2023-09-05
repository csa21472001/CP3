package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {
    FacultyServiceImpl underTest = new FacultyServiceImpl();
    @Test
    void addFaculty_newFaculty_facultyAdded() {
        Faculty faculty = new Faculty(0L, "Slytherin", "green");
        Faculty facultyCheck = underTest.addFaculty(faculty);
        assertEquals(faculty,facultyCheck);
    }

    @Test
    void addFaculty_repeatedFacultyValue_ThrowException()  {
        Faculty faculty = new Faculty(0L, "Slytherin", "green");
        underTest.addFaculty(faculty);
            assertThrows(FacultyException.class,
                    () -> underTest.addFaculty(faculty));
        }

    @Test
    void findFaculty_facultyToFind_returnFoundFaculty() {
        Faculty faculty = new Faculty(0L, "Slytherin", "green");
        underTest.addFaculty(faculty);
        assertEquals(faculty,underTest.findFaculty(1));
    }
    @Test
    void findFaculty_facultyThatCannotBeFound_ThrowException() {
        assertThrows(FacultyException.class,
                () -> underTest.findFaculty(1));
    }

    @Test
    void editFaculty_updateFaculty_facultyUpdated() {
        Faculty faculty = new Faculty(45L, "Slytherin", "green");
        underTest.addFaculty(faculty);
        Faculty updFaculty = new Faculty(1L, "Griffindor", "white");
        Faculty updFacultyCheck = underTest.editFaculty(updFaculty);
        assertEquals(updFaculty, updFacultyCheck);
    }
    @Test
    void editFaculty_repeatedUpdatedStudent_ThrowException() {
        Faculty faculty = new Faculty(1L, "Slytherin", "green");
        underTest.addFaculty(faculty);
        Faculty updFaculty = new Faculty(2L, "Slytherin", "green");
        assertThrows(FacultyException.class,
                () -> underTest.editFaculty(updFaculty));
    }
    @Test
    void deleteFaculty_repeatedRemovalOfTheFaculty_throwException() {
        Faculty faculty = new Faculty(1L, "Slytherin", "green");
        underTest.addFaculty(faculty);
        underTest.deleteFaculty(1);
        assertThrows(FacultyException.class,
                () -> underTest.deleteFaculty(1));
    }
    @Test
    void deleteFaculty_facultyForDelete_facultyRemoved() {
        Faculty faculty = new Faculty(1L, "Slytherin", "green");
        underTest.addFaculty(faculty);
        Faculty updFaculty = underTest.deleteFaculty(1);
        assertEquals(faculty, updFaculty);
    }
    @Test
    void findFacultyWithColor() {
        Faculty faculty = new Faculty(1L, "Slytherin", "green");
        underTest.addFaculty(faculty);
        assertEquals(List.of(faculty), underTest.findFacultyWithColor("green"));
    }
}