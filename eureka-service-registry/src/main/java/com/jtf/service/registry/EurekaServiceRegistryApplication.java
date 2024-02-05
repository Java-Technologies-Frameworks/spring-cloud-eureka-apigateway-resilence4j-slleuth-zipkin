package com.jtf.service.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceRegistryApplication {
    Logger logger = LoggerFactory.getLogger(EurekaServiceRegistryApplication.class);

    public static void main(String[] args) {
        EurekaServiceRegistryApplication obj = new EurekaServiceRegistryApplication();
        obj.logmethod();
        SpringApplication.run(EurekaServiceRegistryApplication.class, args);
    }

    public void logmethod() {
        logger.info("Eureka Service registry started on port 8761");
    }
}
