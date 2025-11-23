package com.project.salesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.project.salesmanagement.repositories")
@ComponentScan(basePackages = {
        "com.project.salesmanagement",
})
public class SalesManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalesManagementApplication.class, args);
    }
}