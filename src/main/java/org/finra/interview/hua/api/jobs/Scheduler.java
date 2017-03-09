package org.finra.interview.hua.api.jobs;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.finra.interview.hua.api.file.controller.FileServiceController;
import org.finra.interview.hua.api.file.entity.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Component
public class Scheduler {

    private static final Logger logger = LogManager.getLogger(Scheduler.class);

    @Value("${email.from}")
    private String from;

    @Value("${email.to}")
    private String to;

    @Value("${email.body}")
    private String body;

    @Value("${email.subject}")
    private String subject;

    @Autowired
    private FileServiceController fileController;

    private static final AmazonSimpleEmailServiceClient sesClient = new AmazonSimpleEmailServiceClient(new BasicAWSCredentials("AKIAJMKNM5F43H4TV32A", "na3nhylqAph4ZMdVMGwPRqAsg74UXIyDvKQkj4Fn"));

    @Scheduled(fixedRate = 60000)
    public void fetchLatestAndSendEmail() {
        logger.info("Start executing job.");
        List<Metadata> metadatas = fileController.getLatestFile();
        if (!metadatas.isEmpty()) {
            Destination destination = new Destination().withToAddresses(new String[]{to});
            StringBuilder builder = new StringBuilder();
            builder.append(String.format(body, metadatas.size()));
            metadatas.forEach(metadata -> {
                builder.append(metadata.getLink()).append("\n");
            });
            
            Content textBody = new Content().withData(builder.toString());
            
            Message message = new Message().withSubject(new Content().withData(subject)).withBody(new Body().withText(textBody));
            SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(destination).withMessage(message);
            try {
                sesClient.setRegion(Region.getRegion(Regions.US_EAST_1));
                sesClient.sendEmail(request);
                logger.info("Finished executing job. Send email successfully.");
            } catch (IllegalArgumentException e) {
                logger.error("Finished executing job. Sending email error. ", e);
            }
        }

    }
}
