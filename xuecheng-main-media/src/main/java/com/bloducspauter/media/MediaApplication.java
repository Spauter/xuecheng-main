package com.bloducspauter.media;

import com.bloducspauter.media.config.MinioConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
/**
 * 媒体管理启动类
 * @author Bloduc Spauter
 *
 */
@SpringBootApplication(scanBasePackages = {"com.bloducspauter.base","com.bloducspauter.media"},scanBasePackageClasses = MinioConfig.class)
@EnableDiscoveryClient
@EnableSwagger2WebMvc
@MapperScan(basePackages = {"com.bloducspauter.media.mapper"})
public class MediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaApplication.class, args);
    }
}
