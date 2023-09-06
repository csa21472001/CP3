package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    StudentServiceImpl underTest = new StudentServiceImpl();
    Student student = new Student(0L, "Harry", 10);

    @Test
    void addStudent_newStudent_studentAdded() {
        Student studentFromCheck = underTest.addStudent(student);
        assertEquals(student, studentFromCheck);
    }

    @Test
    void addStudent_repeatedStudentValue_ThrowException() {
        underTest.addStudent(student);
        assertThrows(StudentException.class,
                () -> underTest.addStudent(student));
    }

    @Test
    void findStudent_studentToFind_returnFoundStudent() {
        underTest.addStudent(student);
        assertEquals(student, underTest.findStudent(1));
    }

    @Test
    void findStudent_idOfStudentThatCannotBeFound_ThrowException() {
        assertThrows(StudentException.class,
                () -> underTest.findStudent(1));
    }

    @Test
    void editStudent_updateStudent_studentUpdated() {
        underTest.addStudent(student);
        Student updStudent = new Student(1L, "Harry Potter", 10);
        Student updStudentCheck = underTest.editStudent(updStudent);
        assertEquals(updStudent, updStudentCheck);
    }

    @Test
    void editStudent_repeatedUpdatedStudent_ThrowException() {
        underTest.addStudent(student);
        Student updStudent = new Student(2L, "Harry", 10);
        assertThrows(StudentException.class,
                () -> underTest.editStudent(updStudent));
    }

    @Test
    void deleteStudent_repeatedRemovalOfTheStudent_throwException() {
        underTest.addStudent(student);
        underTest.deleteStudent(1);
        assertThrows(StudentException.class,
                () -> underTest.deleteStudent(1));
    }

    @Test
    void deleteStudent_idOfStudentForDelete_studentRemoved() {
        underTest.addStudent(student);
        Student updStudent = underTest.deleteStudent(1);
        assertEquals(student, updStudent);
    }

    @Test
    void findStudentWithCertainAge_certainAge_returnListOfStudentsWithCertainAge() {
        underTest.addStudent(student);
        assertEquals(List.of(student), underTest.findStudentAge(10));
    }
}