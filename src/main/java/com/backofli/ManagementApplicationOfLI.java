package com.backofli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;




@ServletComponentScan//在spring下使用三大件
@SpringBootApplication
public class ManagementApplicationOfLI {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplicationOfLI.class, args);
    }

}
