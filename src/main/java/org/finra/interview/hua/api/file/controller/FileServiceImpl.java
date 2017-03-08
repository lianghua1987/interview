package org.finra.interview.hua.api.file.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.MultipartConfigElement;
import org.finra.interview.hua.api.aws.s3.boundary.AmazonS3Service;
import org.finra.interview.hua.api.file.boundary.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private AmazonS3Service s3Service;

//    @Override
//    public File getMetadata() {
//        return new File();
//    }

    @Override
    public InputStream getContent() {
        return null;
    }

    @Override
    public ResponseEntity<String> upload(MultipartFile file) {
        try {
            System.out.println(file == null);
            s3Service.upload(file.getOriginalFilename(), file);
            return new ResponseEntity<>("File uploaded successfully.", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("File uploaded failed. Error Message: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private File convert(MultipartFile multipart) throws IOException {
        File convFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    @Override
    public String getId(String name) {
        return null;
    }

}
