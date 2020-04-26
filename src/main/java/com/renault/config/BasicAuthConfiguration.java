package com.renault.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class BasicAuthConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        // TODO make the DELETE operation possible only for the ADMIN role
        security
                // disable form login security (for login)
                .csrf().disable()
                // requests URL
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/cars").authenticated()
                .antMatchers(HttpMethod.DELETE, "/cars/**").authenticated()
                // authentication type
                .and().httpBasic();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // TODO add password encoder and user details service
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // TODO use BCrypt encoder
        return NoOpPasswordEncoder.getInstance();
    }

}