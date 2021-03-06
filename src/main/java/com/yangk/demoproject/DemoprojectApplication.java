package com.yangk.demoproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@EnableSwagger2
@MapperScan("com.yangk.demoproject.dao.**")
@SpringBootApplication(exclude = {
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class DemoprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoprojectApplication.class, args);
    }

}
