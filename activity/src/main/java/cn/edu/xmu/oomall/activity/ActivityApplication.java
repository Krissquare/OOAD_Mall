package cn.edu.xmu.oomall.activity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */
@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.oomall.core", "cn.edu.xmu.oomall.activity","cn.edu.xmu.privilegegateway.annotation"})
@MapperScan("cn.edu.xmu.oomall.activity.mapper")
@EnableFeignClients(basePackages = "cn.edu.xmu.oomall.activity.microservice")
public class ActivityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivityApplication.class, args);
    }

}
