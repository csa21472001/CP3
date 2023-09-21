package ru.hogwarts.school.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNameAndAge(String name, int age);
    List<Student> findByAge(int age);
//    List<Student> findAll();
    List<Student> findByAgeBetween(int min, int max);
    List<Student> findByFaculty_id(long faculty_id);
    List<Student> findAll();
    @Query("select count(stu) from Student stu")
    Integer findStudentCount();

    @Query(value = "select * from student order by id desc limit :size", nativeQuery = true)
    List<Student> findLastStudents(int size);

    @Query( "select avg(stu.age) from Student stu")
    Integer findAvgAge();




}