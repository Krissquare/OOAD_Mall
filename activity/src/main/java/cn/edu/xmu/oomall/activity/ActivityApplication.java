package cn.edu.xmu.oomall.activity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 12:46
 */
@SpringBootApplication
@MapperScan("cn.edu.xmu.oomall.activity.mapper")
@EnableFeignClients(basePackages = "cn.edu.xmu.oomall.activity.openfeign")
public class ActivityApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivityApplication.class,args);
    }
}
