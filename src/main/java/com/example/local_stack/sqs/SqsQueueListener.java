package com.example.local_stack.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;

import javax.annotation.PostConstruct;
import java.util.concurrent.Future;

public class SqsQueueListener {
    private static final Logger logger = LoggerFactory.getLogger(SqsQueueListener.class);
    @Autowired
    @Qualifier(value = "amazonSqs")
    AmazonSQSAsync sqsAsync;
    @Value("${sqs.queue}")
    String qName;


    @PostConstruct
    public Future<CreateQueueResult> createQueue(){
        logger.info("creating a Q in SQS {}", qName);
        return sqsAsync.createQueueAsync(new CreateQueueRequest(qName));
    }

    @SqsListener("queue-name")
    public void queueListener(Person person) {
        logger.info("Got a message from SQS {}",person);
    }

}
