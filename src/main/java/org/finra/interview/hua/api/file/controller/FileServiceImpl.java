package org.finra.interview.hua.api.file.controller;

import java.io.InputStream;
import org.finra.interview.hua.api.file.boundary.FileService;
import org.finra.interview.hua.api.file.entity.Metadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public Metadata getMetadata() {
        return new Metadata();
    }

    @Override
    public InputStream getContent() {
        return null;
    }

    @Override
    public ResponseEntity<String> upload(MultipartFile file) {
        if(true){
            return new ResponseEntity<String>("File uploaded successfully.", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<String>("File uploaded failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getId(String name) {
       return null;
    }

}
