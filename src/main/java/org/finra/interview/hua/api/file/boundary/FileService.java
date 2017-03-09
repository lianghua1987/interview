package org.finra.interview.hua.api.file.boundary;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.finra.interview.hua.api.file.controller.FileServiceController;
import org.finra.interview.hua.api.file.entity.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/file")
public class FileService {

    private static final Logger logger = LogManager.getLogger(FileService.class);
    
    @Autowired
    private FileServiceController fsController;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Metadata>> getMetadata() {
        List<Metadata> metadatas = fsController.getMetadatas();
        if (metadatas.isEmpty()) {
            return new ResponseEntity<>(metadatas, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(metadatas, HttpStatus.OK);
        }

    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            logger.warn("Empty or Null file");
            return new ResponseEntity<>("File uploaded failed. Empty or NULL file", HttpStatus.BAD_REQUEST);
        }
        try {
            String link = fsController.upload(file);
            logger.info("File uploaded successfully.");
            return new ResponseEntity<>("File uploaded successfully. Link: " + link, HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error("File uploaded failed. ", e);
            return new ResponseEntity<>("File uploaded failed. Error Message: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("search")
    @ResponseBody
    public ResponseEntity<List<Integer>> getIds(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "size", required = false) Long size) {
        List<Integer> ids = fsController.getIds(name, size);
        if (ids.isEmpty()) {
            return new ResponseEntity<>(ids, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(ids, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getContent(@PathVariable("id") Integer id) {
        Metadata metadata = fsController.getMetadataById(id);
        if (metadata == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            try {
                return ResponseEntity
                        .ok()
                        .header("Content-Disposition", "attachment; filename=\"" + metadata.getName() + "\"")
                        .contentLength(metadata.getSize())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(new InputStreamResource(new URL(metadata.getLink()).openStream()));
            } catch (IOException e) {
                logger.error("Get file content failed. ", e);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}
