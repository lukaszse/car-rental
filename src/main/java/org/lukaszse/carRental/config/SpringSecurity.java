package org.lukaszse.carRental.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.lukaszse.carRental.enums.SecurityRole.*;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/cars", "/car", "/send_message", "/add_user", "/css/**", "/js/**", "/images/logo.png", "/cars/*", "/console", "console/**")
                .permitAll()
                .antMatchers("/settings", "/user_administration").hasRole(ROLE_ADMIN.getShortName())
                .antMatchers("/reservations").hasAnyRole(ROLE_USER.getShortName(), ROLE_MANAGER.getShortName(), ROLE_ADMIN.getShortName())
                .antMatchers("/*").hasAnyRole(ROLE_GUEST.getShortName(), ROLE_USER.getShortName(), ROLE_MANAGER.getShortName(), ROLE_ADMIN.getShortName())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
