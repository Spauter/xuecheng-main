package com.bloducspauter.gateway;

import com.bloducspauter.gateway.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * 网关启动类
 * @author Bloduc Spauter
 *
 */
@SpringBootApplication(scanBasePackageClasses = {CorsConfig.class})
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
