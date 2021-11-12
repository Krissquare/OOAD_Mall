package cn.edu.xmu.oomall.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.openfeign.EnableFeignClients

/**
 * @author Ming Qiu
 **/
@SpringBootApplication
@EnableConfigurationProperties
//@ServletComponentScan
@MapperScan("cn.edu.xmu.oomall.shop.mapper")
@EnableFeignClients(basePackages = "cn.edu.xmu.oomall.shop.openfeign")
public class Application {

    public static void main(String[] args) {

        try{SpringApplication.run(Application.class, args);}
        catch(Exception e)
        {
            System.out.println("------>"+e.getMessage());
            e.printStackTrace();
//            System.out.println(e.stack());
        }
    }

}

