package com.example.local_stack.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class S3FileServiceImpl implements S3FileService {
    private static final Logger logger = LoggerFactory.getLogger(S3FileServiceImpl.class);

    private
    @Autowired
    AmazonS3 amazonS3;
    @Autowired
    ResourceLoader resourceLoader;


    @Value(value = "${s3.bucket}")
    String bucketName;


    @Override
    public void createBucket(){
        amazonS3.createBucket(bucketName);
        logger.info("bucket created {}", bucketName);
    }

    @Override
    public void uploadToS3()  {
        Path path = Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "example.png");
        String s3path = "s3://"+bucketName+"/example.png";
        Resource resource = this.resourceLoader.getResource(s3path);
        WritableResource writableResource = (WritableResource) resource;

        logger.info("uploading file {} to s3 ", path.toFile().getName());

        try (OutputStream outputStream = writableResource.getOutputStream()) {
            Files.copy(path, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void downloadFromS3()  {
        Path targetPath = Paths.get("target" + File.separator + "example.png");
        if(targetPath.toFile().exists()){
            targetPath.toFile().delete();
        }
        String s3path = "s3://"+bucketName+"/example.png";
        Resource resource = this.resourceLoader.getResource(s3path);
        logger.info("downloading file {} from s3 ", s3path);

        try (InputStream inputStream = resource.getInputStream()) {
            Files.copy(inputStream, targetPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
