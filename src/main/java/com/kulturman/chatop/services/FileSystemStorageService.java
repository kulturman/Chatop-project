package com.kulturman.chatop.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemStorageService implements FilesStorageService {
    @Value("${app.uploadsDir}")
    private String uploadDir;

    @Override
    public String save(MultipartFile file) throws IOException {
        Path rootPath = Paths.get(uploadDir);
        String newFileName = System.currentTimeMillis() + file.getOriginalFilename();
        Files.copy(file.getInputStream(), rootPath.resolve(newFileName));
        return newFileName;
    }

    @Override
    public String getFileUrl(String filename) {
        return null;
    }

    @Override
    public Resource load(String filename) {
        return null;
    }
}
