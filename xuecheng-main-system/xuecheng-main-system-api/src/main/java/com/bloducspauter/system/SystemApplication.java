package com.bloducspauter.system;


import com.bloducspauter.content.mapper.CourseCategoryMapper;
import com.bloducspauter.system.config.Knife4jConfiguration;
import com.bloducspauter.system.mapper.DictionaryMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * <p>
 *     系统管理启动类
 * </p>
 * @author Bloduc Spauter
 */

@SpringBootApplication(scanBasePackages = {"com.bloducspauter.base","com.bloducspauter.system"},scanBasePackageClasses = {Knife4jConfiguration.class})
@EnableDiscoveryClient
@MapperScan(basePackageClasses = {DictionaryMapper.class, CourseCategoryMapper.class})
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class,args);
    }
}