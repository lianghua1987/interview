package org.finra.interview.hua.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println("org.finra.interview.hua.api.Application.main()");
        SpringApplication.run(Application.class, args);
    }

}