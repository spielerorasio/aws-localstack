package com.example.local_stack.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;

public class SnsPublisher {
    private static final Logger logger = LoggerFactory.getLogger(SnsPublisher.class);

    @Value("${sns.topic}")
    String topicName;

    @Autowired
    NotificationMessagingTemplate notificationMessagingTemplate;
    @Autowired
    @Qualifier(value = "amazonSNS")
    AmazonSNS amazonSNS;

    private CreateTopicResult topic;

    public void registerToNotification(){
        logger.info("register to topic {}", topicName);
        topic = amazonSNS.createTopic(topicName);
        amazonSNS.subscribe(new SubscribeRequest(topic.getTopicArn(),
                "http",
                "http://host.docker.internal:8080/sns-topic-controller/"));

    }

    public void send(EventMessage msg, String subject) {
        try{
            this.notificationMessagingTemplate.sendNotification(topicName, msg, subject);
//        this.notificationMessagingTemplate.sendNotification(topic.getTopicArn(), msg, subject);

        }catch (AmazonSNSException e){
            logger.error(e.getErrorCode() , e);
        }
    }

}
