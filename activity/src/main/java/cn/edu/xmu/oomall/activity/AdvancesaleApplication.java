package cn.edu.xmu.oomall.activity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.oomall.activity","cn.edu.xmu.oomall.core"})
@EnableConfigurationProperties
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@MapperScan("cn.edu.xmu.oomall.activity.mapper")
@EnableFeignClients
public class AdvancesaleApplication {
    public static void main(String[] args){
        SpringApplication.run(AdvancesaleApplication.class,args);
    }
}
