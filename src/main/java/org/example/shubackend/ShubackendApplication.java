package org.example.shubackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ShubackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShubackendApplication.class, args);
    }

}
