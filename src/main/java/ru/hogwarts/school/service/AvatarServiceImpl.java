package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    private final String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    public AvatarServiceImpl(StudentService studentService
            , AvatarRepository avatarRepository
            , @Value("${path.to.avatars.folder}") String avatarsDir) {
        this.avatarsDir = avatarsDir;
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }
    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {

        logger.info( "Был вызван метод uploadAvatar с данными - id студента" + studentId);

        Student student = studentService.findStudent(studentId);
        Path filePath = Path.of(avatarsDir, student.getName() + ".avatar");
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudent_id(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        logger.info("Метод uploadAvatar и сохранен в БД аватар студента " + avatar.getStudent().getName());

        avatarRepository.save(avatar);
    }
    @Override
    public Avatar readFromDB(long id) {
        Optional<Avatar> avatar = avatarRepository.findById(id);

        logger.info( "Был вызван метод readFromDB с данными - id аватарки " + id + ". Метод вернул аватар студента " + avatar.get().getStudent().getName() + " или сообщение об ошибке.");
        return avatarRepository.findById(id)
                .orElseThrow(() -> new AvatarNotFoundException("Тhe avatar is missing"));
    }
    @Override
    public List<Avatar> getPage(int size, int pageNumb) {
        PageRequest page = PageRequest.of (pageNumb, size);
        List<Avatar> avatars = avatarRepository.findAll(page).getContent();
        logger.info( "Был вызван метод getPage с количеством элементов на странице " + size + ". Метод вернул список аватаров" + avatars + " или сообщение об ошибке.");
        return avatarRepository.findAll(page).getContent();
    }
//    private String getExtensions(String fileName) {
//        return fileName.substring(fileName.lastIndexOf("."));
//    }

}
