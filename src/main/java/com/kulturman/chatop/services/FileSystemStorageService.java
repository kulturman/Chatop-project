package com.kulturman.chatop.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemStorageService implements FilesStorageService {
    @Value("${app.uploadsDir}")
    private String uploadDir;
    @Value("${app.url}")
    private String appUrl;
    @Value("${server.port}")
    private String serverPort;

    @Override
    public String save(MultipartFile file) throws IOException {
        Path rootPath = Paths.get(uploadDir);
        String newFileName = System.currentTimeMillis() + file.getOriginalFilename();
        Files.copy(file.getInputStream(), rootPath.resolve(newFileName));
        return newFileName;
    }

    @Override
    public String getFileUrl(String filename) {
        return String.format("%s:%s/images/%s", appUrl, serverPort, filename);
    }

}
