package com.kulturman.chatop.controllers;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/images")
public class StaticFileController {
    @Value("${app.uploadsDir}")
    private String uploadDir;
    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable("fileName") String fileName) {
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(uploadDir + "/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG
        ).body(image);
    }
}
