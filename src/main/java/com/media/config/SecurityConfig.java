package com.media.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.media.security.JwtAutenticationEntryPoint;
import com.media.security.JwtAuthenticationFilter;

@Configuration 
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtAutenticationEntryPoint autenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration ) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
	
	@Bean
	SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
	
		 http.csrf().disable()
         .authorizeHttpRequests((authorize) ->
                 
                 authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                         .requestMatchers("/api/auth/**").permitAll()
                         .anyRequest().authenticated()                         

         ).exceptionHandling( exception -> exception
                 .authenticationEntryPoint(autenticationEntryPoint)
         ).sessionManagement( session -> session
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         );

		 http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		 return http.build();
	}
	

	/*
	 * @Bean public UserDetailsService userDetailsService() {
	 * 
	 * UserDetails akash =
	 * User.builder().username("AKASH").password(passwordEncoder().encode("AKASH")).
	 * roles("USER").build();
	 * 
	 * UserDetails admin =
	 * User.builder().username("ADMIN").password(passwordEncoder().encode("ADMIN")).
	 * roles("ADMIN").build();
	 * 
	 * return new InMemoryUserDetailsManager(akash , admin); }
	 */
	
	
}
