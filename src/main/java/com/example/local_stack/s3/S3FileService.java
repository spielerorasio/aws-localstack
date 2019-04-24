package com.example.local_stack.s3;

public interface S3FileService {
    void createBucket();

    void uploadToS3();

    void downloadFromS3();
}
