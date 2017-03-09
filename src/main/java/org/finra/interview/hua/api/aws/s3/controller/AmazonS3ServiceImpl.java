package org.finra.interview.hua.api.aws.s3.controller;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CanonicalGrantee;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import org.finra.interview.hua.api.aws.s3.boundary.AmazonS3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

    private static final String BUCKET_NAME = "finra-interview-file-storage";

    private static final AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("AKIAJMKNM5F43H4TV32A", "na3nhylqAph4ZMdVMGwPRqAsg74UXIyDvKQkj4Fn"));

    @Override
    public String upload(String key, MultipartFile file) throws AmazonServiceException, AmazonClientException, IOException {
        ObjectMetadata om = new ObjectMetadata();
        om.setContentLength(file.getSize());
        
        PutObjectRequest object = new PutObjectRequest(BUCKET_NAME, key, file.getInputStream(), om);
        object.setCannedAcl(CannedAccessControlList.PublicRead);
        s3client.putObject(object);
        return s3client.getUrl(BUCKET_NAME, key).toString();
    }

}
