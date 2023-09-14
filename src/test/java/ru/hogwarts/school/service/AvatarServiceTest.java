package ru.hogwarts.school.service;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;

import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvatarServiceTest {
    StudentService studentService = mock(StudentService.class);
    AvatarRepository avatarRepository = mock(AvatarRepository.class);
    String avatarsDir = "./src/test/resourses/avatar";
    AvatarService underTest = new AvatarServiceImpl(studentService, avatarRepository, avatarsDir);

    Student student = new Student(1L, "Harry", 10);
    Avatar avatar = new Avatar();

    @Test
    void uploadAvatar_avatarFile_avatarSavedToDBAndDirectory() throws IOException {
        MultipartFile file = new MockMultipartFile("11.pdf"
                , "11.pdf", "pdf", new byte[]{});
        when(studentService.findStudent(student.getId())).thenReturn(student);
        when(avatarRepository.findByStudent_id(student.getId()))
                .thenReturn(Optional.empty());

        underTest.uploadAvatar(student.getId(), file);
        verify(avatarRepository, times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(avatarsDir + "/"
                + student.getName() + ".avatar")));
    }

    @Test
    void readFromDB_correctId_returnAvatar() {
        when(avatarRepository.findById(1L)).thenReturn(Optional.of(avatar));
        assertEquals(avatar, underTest.readFromDB(1L));
    }

    @Test
    void readFromDB_invalidId_throwException() {
        when(avatarRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(AvatarNotFoundException.class, () -> underTest.readFromDB(1L));
    }
}