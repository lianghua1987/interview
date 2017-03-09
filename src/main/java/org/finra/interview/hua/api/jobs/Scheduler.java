package org.finra.interview.hua.api.jobs;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.amazonaws.services.simpleemail.*;
import org.springframework.beans.factory.annotation.Value;

@Component
public class Scheduler {
    
    static final String TO = "hualiang.987@gmail.com"; 
    static final String BODY = "This email was sent through Amazon SES by using the AWS SDK for Java.";
    static final String SUBJECT = "File Alert";
    
    @Value("${email.from}")
    private String from;
    
    @Value("${email.to}")
    private String to;
    
    private static final AmazonSimpleEmailServiceClient sesClient = new AmazonSimpleEmailServiceClient(new BasicAWSCredentials("AKIAJMKNM5F43H4TV32A", "na3nhylqAph4ZMdVMGwPRqAsg74UXIyDvKQkj4Fn"));

    
    @Scheduled(fixedRate = 5000)
    public void fetchLatestAndSendEmail(){
//        Destination destination = new Destination().withToAddresses(new String[]{TO});
//
//        Content subject = new Content().withData(SUBJECT);
//        Content textBody = new Content().withData(BODY);
//        Body body = new Body().withText(textBody);
//        Message message = new Message().withSubject(subject).withBody(body);
//
//        // Assemble the email.
//        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);
//
//        try {
//
//            sesClient.setRegion(Region.getRegion(Regions.US_EAST_1));

//            sesClient.sendEmail(request);
//            System.out.println("Email sent!");
//        } catch (Exception ex) {
//            System.out.println("The email was not sent.");
//            System.out.println("Error message: " + ex.getMessage());
//        }
    }
}
