package com.example.demo_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    // add support for jdbc ... no more hardcoded users
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

//        //For custom tables
//        //define a query to retrieve a user by username
//        jdbcUserDetailsManager.setUsersByUsernameQuery(
//                "select <username>, <password>, <enabled> from <users> where <username>=?");
//        //define a query to retrieve the authorities/roles by username
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "select <username>, <authority> from <authorities> where <username>=?"
//        );

        return jdbcUserDetailsManager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(HttpMethod.GET, "/api/books").hasRole("ANALYST")
                .requestMatchers(HttpMethod.GET, "/api/books/**").hasRole("ANALYST")
                .requestMatchers(HttpMethod.POST, "/api/books").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN"));

        // use Http basic authorization
        http.httpBasic(Customizer.withDefaults());

        // disable Cross Site Request Forgery (CSRF)
        // In general, not required for stateless REST APIs that use GET, POST, PUT, DELETE and/or PATCH
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

}
