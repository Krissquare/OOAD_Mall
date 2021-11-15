package cn.edu.xmu.oomall.coupon;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author RenJieZheng 22920192204334
 */
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("cn.edu.xmu.oomall.coupon.mapper")
@EnableFeignClients(basePackages = "cn.edu.xmu.oomall.coupon.microservice")
public class CouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class, args);
    }

}

