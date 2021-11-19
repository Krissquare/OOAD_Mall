package cn.edu.xmu.oomall.freight;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.oomall.core", "cn.edu.xmu.oomall.freight", "cn.edu.xmu.privilegegateway.annotation"})
@EnableConfigurationProperties
@MapperScan("cn.edu.xmu.oomall.freight.mapper")
//@EnableDiscoveryClient
public class FreightApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreightApplication.class, args);
	}

}
