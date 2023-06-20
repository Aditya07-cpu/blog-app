package com.blog.application.project.Service.Impl;

import com.blog.application.project.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        String fullName = randomId.concat(fileName.substring(fileName.lastIndexOf('.')));

        String fullPath = path + File.separator + fullName;

        File file1 = new File(path);
        if(!file1.exists()) {
            file1.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(fullPath));

        return fullName;
    }

    @Override
    public InputStream getResource(String path, String imageName) throws FileNotFoundException {
        String fullPath = path + File.separator + imageName;
        InputStream inputStream = new FileInputStream(fullPath);

        return inputStream;
    }
}
