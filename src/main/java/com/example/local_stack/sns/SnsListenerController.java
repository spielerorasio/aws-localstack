package com.example.local_stack.sns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationSubject;
import org.springframework.cloud.aws.messaging.endpoint.NotificationStatus;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationMessageMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationSubscriptionMapping;
import org.springframework.cloud.aws.messaging.endpoint.annotation.NotificationUnsubscribeConfirmationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/sns-topic-controller")
public class SnsListenerController {
    private static final Logger logger = LoggerFactory.getLogger(SnsListenerController.class);
    @Autowired
    SnsPublisher snsPublisher;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public boolean testSnsListener(){
        logger.info("publishing an event");
        snsPublisher.send(new EventMessage("MyEvent"),"mySubject");
        return true;
    }


    @NotificationSubscriptionMapping
    public void handleSubscriptionMessage(NotificationStatus status) throws IOException {
        //We subscribe to start receive the message
        status.confirmSubscription();
    }

    @NotificationMessageMapping
    @RequestMapping(value = "/testGet", method = RequestMethod.GET)
    public void handleNotificationMessage(@NotificationSubject String subject, @NotificationMessage String message) {
        // ...
        logger.info("subject {} message {}", subject, message);
    }

    @NotificationUnsubscribeConfirmationMapping
    public void handleUnsubscribeMessage(NotificationStatus status) {
        //e.g. the client has been unsubscribed and we want to "re-subscribe"
        status.confirmSubscription();
    }
}
