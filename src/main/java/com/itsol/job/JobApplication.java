package com.itsol.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JobApplication {
    private static final Logger logger = LoggerFactory.getLogger(JobApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JobApplication.class, args);
        Environment env = context.getEnvironment();
        logger.info("Server listening on http://localhost:{}/swagger-ui/index.html#/", env.getProperty("server.port"));
    }

}
