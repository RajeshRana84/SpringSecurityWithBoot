package com.example.sbt.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

	 @Autowired
	 DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		
		// In memory authentication
		/*authenticationMgr.inMemoryAuthentication()
			.withUser("jduser").password("jdu@123").authorities("ROLE_USER")
			.and()
			.withUser("jdadmin").password("jda@123").authorities("ROLE_USER","ROLE_ADMIN");*/
		
		// JDBC authetication
		authenticationMgr.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
				"select username,password, enabled from users where username=?")
		.authoritiesByUsernameQuery( "select username, role from user_roles where username=?");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http.authorizeRequests()
			.antMatchers("/homePage").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/userPage").access("hasRole('ROLE_USER')")
			.antMatchers("/adminPage").access("hasRole('ROLE_ADMIN')")
			.and()
				.formLogin().loginPage("/loginPage")
				.defaultSuccessUrl("/homePage")
				.failureUrl("/loginPage?error")
				.usernameParameter("username").passwordParameter("password")				
			.and()
				.logout().logoutSuccessUrl("/loginPage?logout"); 
		
	}
}