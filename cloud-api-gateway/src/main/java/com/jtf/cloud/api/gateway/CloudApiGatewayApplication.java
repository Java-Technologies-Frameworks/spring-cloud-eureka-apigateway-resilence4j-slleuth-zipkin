package com.jtf.cloud.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudApiGatewayApplication {

    Logger logger = LoggerFactory.getLogger(CloudApiGatewayApplication.class);

    public static void main(String[] args) {
        CloudApiGatewayApplication obj = new CloudApiGatewayApplication();
        obj.logMethod();
        SpringApplication.run(CloudApiGatewayApplication.class, args);
    }

    public void logMethod() {
        logger.info("API gateway received the request ...");
    }
}
