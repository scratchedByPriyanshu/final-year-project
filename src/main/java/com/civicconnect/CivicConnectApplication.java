package com.civicconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CivicConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CivicConnectApplication.class, args);
        System.out.println("=================================================");
        System.out.println("   CIVIC CONNECT REST API BACKEND IS RUNNING    ");
        System.out.println("   URL: http://localhost:8080                    ");
        System.out.println("=================================================");
    }
}
