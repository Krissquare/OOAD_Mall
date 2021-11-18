package cn.edu.xmu.oomall.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author Ming Qiu
 **/
@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.oomall.core.*", "cn.edu.xmu.oomall.shop.*"})
@EnableConfigurationProperties
@MapperScan("cn.edu.xmu.oomall.shop.mapper")
@EnableFeignClients(basePackages = "cn.edu.xmu.oomall.shop.microservice")
//@EnableDiscoveryClient
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

}

