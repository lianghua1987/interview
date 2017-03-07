package org.finra.interview.hua.api.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class Scheduler {
    
    @Scheduled(fixedRate = 5000)
    public void fetchLatestAndSendEmail(){
        System.out.println("----------------------schedule----------------------");
    }
}
