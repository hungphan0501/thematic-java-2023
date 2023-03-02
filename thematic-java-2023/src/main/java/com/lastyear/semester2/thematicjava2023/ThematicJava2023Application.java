package com.lastyear.semester2.thematicjava2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan("com.lastyear.semester2.thematicjava2023.model")
//@EnableJpaRepositories("com.lastyear.semester2.thematicjava2023.repository")
public class ThematicJava2023Application {

    public static void main(String[] args) {
        SpringApplication.run(ThematicJava2023Application.class, args);
    }

}
