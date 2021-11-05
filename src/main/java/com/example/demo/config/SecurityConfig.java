package com.example.demo.config;

import com.example.demo.common.ApiLogFilter;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("testid")
                .password(passwordEncoder().encode("testPassword"))
                .roles("USER")
                .and()
                .withUser("testid2")
                .password(passwordEncoder().encode("testPassword2"))
                .roles("USER", "ADMIN");

         auth.userDetailsService( username -> {
             final User user = userRepository.findByEmail(username);

             if(user == null) {
                 return null;
             }

             return org.springframework.security.core.userdetails.User.builder()
                     .username(user.getEmail())
                     .password(user.getPassword())
                     .roles("USER")
                     .build();
         });

        // auth.authenticationProvider()
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .addFilterBefore(new ApiLogFilter(objectMapper), UsernamePasswordAuthenticationFilter.class)

                .authorizeRequests()
                //.antMatchers("/test/**").hasRole("ADMIN")
                //.antMatchers("/api/**").authenticated()
                //.antMatchers("/api/**").hasRole("USER")
                .anyRequest().permitAll()

                .and()
                .formLogin()

                .and()
                .logout()
                .logoutUrl("/api/v1/users/sign-out");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @SuppressWarnings("unused")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}