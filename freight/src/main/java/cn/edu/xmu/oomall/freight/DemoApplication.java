package cn.edu.xmu.oomall.freight;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@SpringBootApplication(scanBasePackages = {"cn.edu.xmu.oomall"})
@MapperScan("cn.edu.xmu.oomall.freight.mapper")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
