package com.jtf.cloud.config.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class CloudConfigServerApplication {

    Logger logger = LoggerFactory.getLogger(CloudConfigServerApplication.class);

    public static void main(String[] args) {
        CloudConfigServerApplication obj = new CloudConfigServerApplication();
        obj.logMethod();
        SpringApplication.run(CloudConfigServerApplication.class, args);
    }

    public void logMethod() {
        logger.info("Cloud config server started and load the external common properties froim github repo ::");
    }
}
