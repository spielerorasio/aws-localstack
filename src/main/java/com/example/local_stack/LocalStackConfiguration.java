package com.example.local_stack;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.example.local_stack.sns.SnsPublisher;
import com.example.local_stack.sqs.SqsQueueListener;
import com.example.local_stack.sqs.SqsQueueSender;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalStackConfiguration {

    /*   SQS   */
    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs) {
        return new QueueMessagingTemplate(amazonSqs);
    }

    @Bean
    public SqsQueueSender sqsQueueSender() {
        return new SqsQueueSender();

    }

    @Bean
    public SqsQueueListener sqsQueueListener() {
        return new SqsQueueListener();

    }

    /*   SNS   */
    @Bean
    public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS) {
        return new NotificationMessagingTemplate(amazonSNS);
    }
    @Bean
    public SnsPublisher snsPublisher() {
        return new SnsPublisher();

    }

}