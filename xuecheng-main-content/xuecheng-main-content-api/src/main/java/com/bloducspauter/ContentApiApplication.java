package com.bloducspauter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 内容管理启动类
 * @author Bloduc Spauter
 *
 */
@SpringBootApplication(scanBasePackages = {"com.bloducspauter.content"})
public class ContentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApiApplication.class, args);
    }
}
