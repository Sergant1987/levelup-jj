package com.marchenko.medcards.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class PatientSecurityConfiguration extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and()
                .authorizeRequests().antMatchers("/patients/registration").not().fullyAuthenticated()
                .and()
                .antMatcher("/patients/**")
                .authorizeRequests().anyRequest().hasAnyAuthority("PATIENT")
                .and()
                .formLogin()
                .loginPage("/patients/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/patients/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/patients/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");
    }

}
