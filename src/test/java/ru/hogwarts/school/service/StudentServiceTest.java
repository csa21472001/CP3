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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
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
    Student student = new Student(1L, "aHarry", 10);
    Student student1 = new Student(2L, "aRon", 11);
    Student student2 = new Student(3L, "aGermiona", 9);
    Faculty faculty = new Faculty(4L, "Puffendor", "yellow");


    @BeforeEach
    void beforeEach() {
        studentRepository.deleteAll();
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
    void findByAgeBetween_minAndMax_returnListOfStudents() {
        int min = 10;
        int max = 15;
        when(studentRepository.findByAgeBetween(min, max))
                .thenReturn(List.of(student));
        assertEquals(List.of(student), underTest.findByAgeBetween(min, max));
    }

    @Test
    void findStudentCount__returnNumberOffAllStudents() {
        when(studentRepository.findStudentCount()).thenReturn(3);
        assertEquals(3,underTest.findStudentCount());
    }
    @Test
    void findLastStudents__returnFiveStudentsWithHighestId() {
        when(studentRepository.findLastStudents(3))
                .thenReturn(List.of(student2,student1,student));
        assertEquals(List.of(student2,student1,student),underTest.findLastStudents());
    }
    @Test
    void findAvgAge__returnAvgAgeOfAllStudents() {
        when(studentRepository.findAvgAge()).thenReturn(14);
        assertEquals(14,underTest.findAvgAge());
    }
    @Test
    void findNameWithFirstLetterIsA__returnListOfSortedNames() {
        when(studentRepository.findAll()).thenReturn(List.of(student,student1,student2));
        List<String> names = underTest.findNameWithFirstLetterIsA();
        assertThat(names).contains(student.getName().toUpperCase(),student1.getName().toUpperCase());
        assertThat(names).isSorted();
    }
    @Test
    void findAvgAgeByStream__returnAvgAgeOfAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student,student1,student2));
        double avgAge1= 12.0d;
        Double avgAge = underTest.findAvgAgeByStream();
        assertEquals(10,avgAge);
//        Assertions.assertThat(avgAge).satisfies(age -> {
//            Assertions.assertThat(age).isBetween( avgAge1, avgAge1 + 1.0);
//        });
    }
    @Test
    void findAvgAgeByStream__returnZero() {
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());
        int orElseValue = 0;
        assertEquals(orElseValue, underTest.findAvgAgeByStream());
    }


//        @Test
//    void findByNameAndAge_wrongNameAndAge_thrownException() {
//        when(studentRepository.findByNameAndAge(student.getName(), student.getAge()))
//                .thenReturn(Optional.empty());
//        assertThrows(StudentException.class,
//                () -> underTest.findByNameAndAge(student.getName(), student.getAge()));
//    }

//    @Test
//    void findByNameAndAge_nameAndAge_returnFaculty() {
//        when(studentRepository.findByNameAndAge(student.getName(), student.getAge()))
//                .thenReturn(Optional.of(student));
//        assertEquals(faculty, underTest.findByNameAndAge(student.getName(), student.getAge()));
//    }
}