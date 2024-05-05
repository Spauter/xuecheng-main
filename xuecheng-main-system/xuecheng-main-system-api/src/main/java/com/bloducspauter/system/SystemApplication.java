package com.bloducspauter.system;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * <p>
 *     系统管理启动类
 * </p>
 * @author Bloduc Spauter
 */
@EnableScheduling
@SpringBootApplication
@EnableSwagger2WebMvc
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }
}