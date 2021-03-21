package com.marchenko.medcards.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@Order(3)
public class NotAuthenticatedUserConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // страницы исключения для авторизации
                .antMatchers("/", "/about", "/contacts").permitAll()
                .antMatchers("/**/*.js", "/**/*.css").permitAll()
                .anyRequest().denyAll()
//                .antMatchers().not().fullyAuthenticated()
                .and().csrf();
    }

}
