package com.kulturman.chatop.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FilesStorageService {
    String save(MultipartFile file) throws IOException;
    String getFileUrl(String filename);
    Resource load(String filename);

}