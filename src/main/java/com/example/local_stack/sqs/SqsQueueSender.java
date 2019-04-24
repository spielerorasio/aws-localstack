package com.example.local_stack.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;

public class SqsQueueSender {
    private static final Logger logger = LoggerFactory.getLogger(SqsQueueSender.class);

    @Autowired
    QueueMessagingTemplate queueMessagingTemplate;


    @Value("${sqs.queue}")
    String qName;




    public void send(Person person) {
        logger.info("sending a message to queue {} message {}", qName, person);
        this.queueMessagingTemplate.convertAndSend(qName,  person );
    }

}
