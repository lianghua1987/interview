package org.finra.interview.hua.api.file.boundary;

import java.io.InputStream;
import java.util.List;
import org.finra.interview.hua.api.file.entity.Metadata;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/file")
public interface FileService {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Metadata>> getMetadata();

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file);

    // Optional
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Long>> getIds(@RequestParam(value="name") String name, @RequestParam(value="size") String size);

//    @RequestMapping(value="/file/{test}", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<Resource> getContent(@PathVariable("test") String test);

    @GetMapping(value="/{test}")
    @ResponseBody
    public String getContent(@PathVariable("test") String test);
}
