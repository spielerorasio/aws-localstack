package com.example.local_stack;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
public class LocalStackLocalConfiguration {

    @Value("${cloud.aws.region.static}")
    String awsRegion;

    @Value("${localstack.sqs.url}")
    String localStackSqsUrl;

    @Value("${localstack.s3.url}")
    String localStackS3Url;

    @Value("${localstack.sns.url}")
    String localStackSnsUrl;

    @Bean
    public AmazonSQSAsync amazonSqs() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localStackSqsUrl, awsRegion))
                .build();
    }



    @Bean
    public AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localStackSnsUrl, awsRegion))
                .build();
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localStackS3Url, awsRegion))
                .withPathStyleAccessEnabled(true)
                .build();
    }

}
