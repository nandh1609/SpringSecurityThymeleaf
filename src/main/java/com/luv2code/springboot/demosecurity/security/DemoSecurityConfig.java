package com.luv2code.springboot.demosecurity.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    //Below we use the JDBC userdetails manager

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){  //here datasiurce will is autoconfigured since it has default mappings in workbench

        //here we're giving custom configuration for accessing the dB table
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        //Query to retrieve user from username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from members where user_id=?"
        );  //above the "?" will be getting from the username that we enter

        //query to retrieve roles from username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?"
        );

        return jdbcUserDetailsManager;


        //return jdbcUserDetailsManager; //this will autoconfigure only if we give the format in the DB as defined by the spring
    }



    /*
    //the below inmemory userdetails which is manula and not from database

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")  //here {noop} which means no operation password encoder initially we need to have a password encoder like sha 1 but as for demo we don't use any encode so noop
                .roles("EMPLOYEE")   //here we gave Employee but the default format for roles in the workbench(DB) is ROLE_EMPLOYEE
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER")
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);

    }
    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer->
                        configurer
                                .requestMatchers("/").hasRole("EMPLOYEE")   //here trhe role from db is ROLE_EMPLOYEE but ROLE is default and is hidden here
                                .requestMatchers("/leaders/**").hasRole("MANAGER")  //here the ** is all the subdireectories after the leaders as well will also be authenticated
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .anyRequest().authenticated() // w/o the above ones every roles are authenticated
        )
                .formLogin(form  -> form
                        .loginPage("/showMyLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
                ).logout(logout -> logout
                        .permitAll())
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied"));  //here this access-denied will for the mapping of the access denied

        return http.build();
    }

}
