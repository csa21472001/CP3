package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @InjectMocks
    StudentServiceImpl underTest;

    @Mock
    StudentRepository studentRepository;

    //    StudentServiceImpl underTest = new StudentServiceImpl(54);
    Student student = new Student(1L, "Harry", 10);
    Faculty faculty = new Faculty(4L, "Puffendor", "yellow");

    @BeforeEach
    void beforeEach() {
        student.setFaculty(new Faculty(4L, "Puffendor", "yellow"));
    }

    @Test
    void addStudent_newStudent_studentAdded() {
        when(studentRepository.findByNameAndAge(student.getName(), student.getAge()))
                .thenReturn(Optional.empty());
        when(studentRepository.save(student)).thenReturn(student);
        Student studentCheck = underTest.addStudent(student);
        assertEquals(student, studentCheck);
//        assertTrue(underTest.getAll().contains(student));
    }

    @Test
    void addStudent_repeatedStudentValue_throwException() {
        when(studentRepository.findByNameAndAge(student.getName(), student.getAge()))
                .thenReturn(Optional.of(student));
//        underTest.addStudent(student);
        assertThrows(StudentException.class,
                () -> underTest.addStudent(student));
    }

    @Test
    void findStudent_studentToFind_returnFoundStudent() {
        when(studentRepository.findById(4L)).thenReturn(Optional.of(student));
        Student studentCheck = underTest.findStudent(4);
        assertEquals(student, studentCheck);
    }

    @Test
    void findStudent_studentThatCannotBeFound_throwException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(StudentException.class,
                () -> underTest.findStudent(1L));
    }

    @Test
    void editStudent_updateStudent_studentUpdated() {
//        underTest.addStudent(student);
        Student updStudent = new Student(1L, "Griffindor", 13);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(updStudent));
        when(studentRepository.save(updStudent)).thenReturn(updStudent);
        Student updStudentCheck = underTest.editStudent(updStudent);
        assertEquals(updStudent, updStudentCheck);
    }

    @Test
    void editStudent_repeatedUpdatedStudent_throwException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(StudentException.class,
                () -> underTest.editStudent(student));
    }

    @Test
    void deleteStudent_repeatedRemovalOfTheStudent_throwException() {
        when(studentRepository.findById(4L)).thenReturn(Optional.empty());
        assertThrows(StudentException.class,
                () -> underTest.deleteStudent(4));
    }

    @Test
    void deleteStudent_studentToDelete_studentRemoved() {
        when(studentRepository.findById(4L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).deleteById(4L);
        Student updStudent = underTest.deleteStudent(4L);
        assertEquals(student, updStudent);
    }

    @Test
    void findStudentWithAge_сertainAge_listOfStudentWithCertainAge() {
        when(studentRepository.findByAge(10)).thenReturn(List.of(student));
        assertEquals(List.of(student), underTest.findStudentWithAge(10));
    }

    @Test
    void findByNameAndAge_wrongNameAndAge_thrownException() {
        when(studentRepository.findByNameAndAge(student.getName(), student.getAge()))
                .thenReturn(Optional.empty());
        assertThrows(StudentException.class,
                () -> underTest.findByNameAndAge(student.getName(), student.getAge()));
    }

    @Test
    void findByNameAndAge_nameAndAge_returnFaculty() {
        when(studentRepository.findByNameAndAge(student.getName(), student.getAge()))
                .thenReturn(Optional.of(student));
        assertEquals(faculty, underTest.findByNameAndAge(student.getName(), student.getAge()));
    }

    @Test
    void findByAgeBetween_minAndMax_returnListOfStudents() {
        int min = 10;
        int max = 15;
        when(studentRepository.findByAgeBetween(min, max))
                .thenReturn(List.of(student));
        assertEquals(List.of(student), underTest.findByAgeBetween(min, max));
    }


}