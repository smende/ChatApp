package io.msd.chat.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class OAuthSecurityConfig 
extends WebSecurityConfigurerAdapter
{
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(a -> a
                .antMatchers("/", "/error", "/webjars/**","/login").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login().and().csrf().disable();
    }
	
}