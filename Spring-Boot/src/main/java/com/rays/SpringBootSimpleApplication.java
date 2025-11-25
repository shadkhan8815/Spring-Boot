package com.rays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rays.common.FrontCtl;

@SpringBootApplication
public class SpringBootSimpleApplication {
	
	@Autowired
	private FrontCtl frontctl ;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSimpleApplication.class, args);
		System.out.println("spring boot application is start on http://localhost:8080");
	}
	
	@Bean
	public WebMvcConfigurer webMvcConfig() {
		
		return new WebMvcConfigurer() {
			
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(frontctl).addPathPatterns("/**").excludePathPatterns("/Auth/**");
			}
		};
	}

}
