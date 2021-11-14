package cn.edu.xmu.oomall.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ActivityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivityApplication.class,args);
    }
}
