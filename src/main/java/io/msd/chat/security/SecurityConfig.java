package io.msd.chat.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  auth.authenticationProvider(preauthAuthProvider());
	 }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(ssoFilter(), RequestHeaderAuthenticationFilter.class)
			.authenticationProvider(
	  	       preauthAuthProvider())
	        .authorizeRequests()
	        .antMatchers( "/", "/js/**", "/css/**", "/images/**").permitAll()
	        .anyRequest().authenticated()
//            .and()
//            .formLogin()
//                .permitAll()
//            .and().httpBasic()
//            .and()
//            .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .clearAuthentication(true)
//                .permitAll()
            .and().csrf().
            disable();
//            csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    	}

	 @Bean
	 public UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() {
	  UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = 
	                 new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>();
	  wrapper.setUserDetailsService(userDetailsService);
	  return wrapper;
	 }

	 @Bean
	 public PreAuthenticatedAuthenticationProvider preauthAuthProvider() {
	  PreAuthenticatedAuthenticationProvider preauthAuthProvider = 
	   new PreAuthenticatedAuthenticationProvider();
	  preauthAuthProvider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
	  return preauthAuthProvider;
	 }

	 @Bean
	 public SSORequestHeaderAuthenticationFilter ssoFilter() throws Exception {
	  SSORequestHeaderAuthenticationFilter filter = new SSORequestHeaderAuthenticationFilter();
	  filter.setAuthenticationManager(authenticationManager());
	  return filter;
	 }
	
	
}

class SSORequestHeaderAuthenticationFilter extends RequestHeaderAuthenticationFilter{
	
	@Value("${local_user}")
	private String localUser;
	
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return localUser;
	}
}
