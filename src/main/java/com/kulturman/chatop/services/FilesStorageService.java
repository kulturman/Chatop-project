package com.kulturman.chatop.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    void save(MultipartFile file);
    Resource load(String filename);

}