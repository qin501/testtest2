package cn.wsq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
// 扫描mybatis mapper包路径
@MapperScan(basePackages={"cn.wsq.mapper"})
// 扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages= {"cn.wsq", "idworker"})
public class Application {
	@Bean
	public SpringUtil getSpingUtil() {
		return new SpringUtil();
	}
    @Bean
	public MultipartConfigElement multipartConfigElement(){
		MultipartConfigFactory factory=new MultipartConfigFactory();
		factory.setMaxFileSize("10240KB");
		factory.setMaxRequestSize("10240KB");
		return factory.createMultipartConfig();
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
