package com.example.local_stack;

import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.example.local_stack.s3.S3FileService;
import com.example.local_stack.sns.EventMessage;
import com.example.local_stack.sns.SnsPublisher;
import com.example.local_stack.sqs.Person;
import com.example.local_stack.sqs.SqsQueueSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class LocalStackApplication {


	public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
		ConfigurableApplicationContext context = SpringApplication.run(LocalStackApplication.class, args);
		SqsQueueSender sqsQueueSender = context.getBean(SqsQueueSender.class);
		TimeUnit.SECONDS.sleep(2);

		sqsQueueSender.send(new Person("John", "Doe"));


		S3FileService s3FileService = context.getBean(S3FileService.class);
		s3FileService.createBucket();
		s3FileService.uploadToS3();
		s3FileService.downloadFromS3();

		SnsPublisher snsPublisher = context.getBean(SnsPublisher.class);
		snsPublisher.registerToNotification();
		TimeUnit.SECONDS.sleep(2);

		snsPublisher.send(new EventMessage("MyEvent"),"mySubject");
	}


}
