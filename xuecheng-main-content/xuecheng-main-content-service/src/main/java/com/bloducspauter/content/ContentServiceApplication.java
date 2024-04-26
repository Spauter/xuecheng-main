package com.bloducspauter.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 *
 * content-service启动类
 * @author Bloduc Spauter
 *
 */
@SpringBootApplication
@MapperScan("com.bloducspauter.content.mapper")
public class ContentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }
}
