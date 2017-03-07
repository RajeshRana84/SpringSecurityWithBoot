package com.example.sbt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.example.sbt.security.LoginSecurityConfig;

@EnableWebMvc
@Configuration
@ComponentScan({ "com.example.sbt.*" })
@Import(value = { LoginSecurityConfig.class })
public class LoginApplicationConfig {
	
	
	
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	
	
    @Bean(name = "dataSource")
 public DriverManagerDataSource dataSource() {
     DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
     driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
     driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/infinity");
     driverManagerDataSource.setUsername("root");
     driverManagerDataSource.setPassword("root");
     return driverManagerDataSource;
 }
	
}
