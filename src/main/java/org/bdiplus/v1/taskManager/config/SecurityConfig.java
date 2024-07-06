package org.bdiplus.v1.taskManager.config;

import org.bdiplus.v1.taskManager.security.JwtAuthenticationEntryPoint;
import org.bdiplus.v1.taskManager.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //             .csrf(csrf -> csrf.disable())
    //             .authorizeHttpRequests(authorize -> authorize
    //                     .requestMatchers("/**","/swagger-ui/**","/h2-console/**","/apis/authenticate", "/swagger-resources/*",
    //                             "/v3/api-docs/**").permitAll()
    //                     .requestMatchers(HttpMethod.DELETE, "/users").hasRole("ADMIN")
    //                     .anyRequest().authenticated())
    //             .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
    //                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    //              http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



    //     return http.build();
    // }
      @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/swagger-ui/**", "/h2-console/**", "/apis/authenticate", "/swagger-resources/**", "/v3/api-docs/**").permitAll() // Permit these endpoints
                .requestMatchers(HttpMethod.DELETE, "/users").hasRole("ADMIN") // Restrict DELETE on /users to ADMIN role
                .anyRequest().authenticated() // All other requests need authentication
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // Handle unauthorized access
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Set session management to stateless

        // Add JWT authentication filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Allow access to H2 console frames
        http.headers(headers -> headers.frameOptions().sameOrigin());

        return http.build();
    }
}
