package com.bloducspauter.content;
import com.bloducspauter.content.config.RequestFilter;
import com.bloducspauter.system.mapper.DictionaryMapper;
import com.bloducspauter.system.service.CourseCategoryService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * 内容管理启动类
 * @author Bloduc Spauter
 *
 */
@SpringBootApplication(scanBasePackages = {"com.bloducspauter.content","com.bloducspauter.base"}
        ,scanBasePackageClasses = {CourseCategoryService.class, RequestFilter.class})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.bloducspauter.content.mapper"},basePackageClasses = {DictionaryMapper.class})
public class ContentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentApiApplication.class, args);
    }
}
