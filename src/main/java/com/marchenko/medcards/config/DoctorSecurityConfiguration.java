package com.marchenko.medcards.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(2)
@Configuration
public class DoctorSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and()
                .authorizeRequests().antMatchers("/doctors/registration").not().fullyAuthenticated()
                .and()
                .antMatcher("/doctors/**")
                .authorizeRequests().anyRequest().hasAnyAuthority("DOCTOR")
                .and()
                .formLogin()
                .loginPage("/doctors/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/doctors/")
                .permitAll()
                .and()
                .logout()
                .and()
                .logout()
                .logoutUrl("/doctors/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");
    }
}
