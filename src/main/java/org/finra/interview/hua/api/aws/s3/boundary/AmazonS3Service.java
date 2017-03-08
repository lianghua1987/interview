package org.finra.interview.hua.api.aws.s3.boundary;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Controller
public interface AmazonS3Service {
    
    public String upload(String key, MultipartFile stream) throws AmazonServiceException, AmazonClientException, IOException ;
    
}
