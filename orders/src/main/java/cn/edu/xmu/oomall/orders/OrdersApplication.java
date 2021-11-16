package cn.edu.xmu.oomall.orders;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("cn.edu.xmu.oomall.orders.mapper")
public class OrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }

}