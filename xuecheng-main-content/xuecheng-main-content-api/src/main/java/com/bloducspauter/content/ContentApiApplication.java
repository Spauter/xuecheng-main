package com.bloducspauter.content;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 内容管理启动类
 * @author Bloduc Spauter
 *
 */
@SpringBootApplication(scanBasePackages = {"com.bloducspauter.content"})
@EnableDiscoveryClient
public class ContentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApiApplication.class, args);
    }
}
