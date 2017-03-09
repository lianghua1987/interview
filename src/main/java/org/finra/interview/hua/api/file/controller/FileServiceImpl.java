package org.finra.interview.hua.api.file.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.finra.interview.hua.api.aws.s3.boundary.AmazonS3Service;
import org.finra.interview.hua.api.file.boundary.FileService;
import org.finra.interview.hua.api.file.entity.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileServiceImpl implements FileService {

    @Autowired
    private AmazonS3Service s3Service;

    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    @Transactional
    public ResponseEntity<List<Metadata>> getMetadata() {
        List<Metadata> metadatas = em.createNamedQuery("Metadata.getAll", Metadata.class).getResultList();
        if(metadatas.isEmpty()){
            return new ResponseEntity<>(metadatas, HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(metadatas, HttpStatus.OK);
        }
    }
    
    @Override
    public ResponseEntity<List<Long>> getIds(String name,String size) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<String> upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return new ResponseEntity<>("File uploaded failed. Empty or NULL file", HttpStatus.BAD_REQUEST);
        }

        try {
            String link = s3Service.upload(file.getOriginalFilename(), file);
            Metadata metadata = new Metadata();
            metadata.setName(file.getOriginalFilename());
            metadata.setSize(file.getSize());
            metadata.setLink(link);
            metadata.setUploadTime(Calendar.getInstance().getTime());
           
            em.persist(metadata);
            return new ResponseEntity<>("File uploaded successfully.", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("File uploaded failed. Error Message: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    @Override
//    @Transactional
//    public ResponseEntity<Resource> getContent(String test){
//        Metadata metadata = em.find(Metadata.class, Integer.parseInt(test));
//        ByteArrayResource resource = null;
//        if(metadata == null){
//            return new ResponseEntity<>(resource, HttpStatus.BAD_REQUEST);
//        } else{
//            try {
//                Path path = Paths.get(metadata.getLink());
//                resource = new ByteArrayResource(Files.readAllBytes(path));
//                return new ResponseEntity<>(resource, HttpStatus.OK);
//            } catch (IOException e) {
//                return new ResponseEntity<>(resource, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//    }

    @Override
    public String getContent(String test) {
        System.out.println(test);
        return null;
    }

 
}
