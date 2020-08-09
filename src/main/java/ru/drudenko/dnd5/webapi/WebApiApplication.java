package ru.drudenko.dnd5.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableCaching
@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.drudenko.dnd5.webapi")
@EntityScan(basePackages = "ru.drudenko.dnd5.webapi")
public class WebApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WebApiApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApiApplication.class);
    }

}


