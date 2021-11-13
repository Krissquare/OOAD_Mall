package cn.edu.xmu.oomall.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author Ming Qiu
 **/
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("cn.edu.xmu.oomall.goods.mapper")
@EnableFeignClients(basePackages = "cn.edu.xmu.oomall.goods.openfeign")
public class GoodsApplication {

    public static void main(String[] args) {

        SpringApplication.run(GoodsApplication.class, args);

    }

}

