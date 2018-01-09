package com.rest.fileupload.rp.restfileuploadrp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.rest.fileupload.rp.restfileuploadrp.storage.FileLocationProperties;
import com.rest.fileupload.rp.restfileuploadrp.storage.UploadingService;


//added comments
@SpringBootApplication
@EnableConfigurationProperties(FileLocationProperties.class)
public class RestFileuploadRpApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestFileuploadRpApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UploadingService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
