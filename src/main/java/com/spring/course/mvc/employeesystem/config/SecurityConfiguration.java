package com.spring.course.mvc.employeesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails john = User
                .builder()
                .username("john")
                .password("{noop}password")
                .roles("EMPLOYEE")
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}password")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}password")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource datasource) {
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(datasource);
//        userDetailsManager.setUsersByUsernameQuery("SELECT");
//        return userDetailsManager;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/auth/login").anonymous()
                                .requestMatchers("/employees/update/**").hasAnyRole("MANAGER","ADMIN")
                                .requestMatchers("/employees/delete/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).formLogin(form ->
                        form
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/auth/login")
                                .defaultSuccessUrl("/employees")
                                .permitAll()

                )
                .exceptionHandling(configurer -> configurer.accessDeniedPage("/auth/error"))
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
}
