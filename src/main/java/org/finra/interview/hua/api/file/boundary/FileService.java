package org.finra.interview.hua.api.file.boundary;

import java.io.InputStream;
import org.finra.interview.hua.api.file.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api")
public interface FileService {
    
    @RequestMapping(path = "/meta", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<File> getMetadata();
    
    @RequestMapping(method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public  ResponseEntity<String> upload(@RequestParam("file") MultipartFile file);
    
    // Optional
    @RequestMapping(method=RequestMethod.GET)
    public @ResponseBody InputStream getContent();
    
    
    @RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String getId(@RequestParam("name") String name);
}
