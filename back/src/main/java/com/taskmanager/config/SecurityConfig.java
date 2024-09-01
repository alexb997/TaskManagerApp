package com.taskmanager.config;

import com.taskmanager.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/tasks").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/api/users/login");
        return filter;
    }

    public static class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

        private final ObjectMapper objectMapper = new ObjectMapper();

        public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
            setAuthenticationManager(authenticationManager);
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
            try {
                // Read JSON payload from the request
                UserCredentials credentials = objectMapper.readValue(request.getInputStream(), UserCredentials.class);

                String username = credentials.getUsername();
                String password = credentials.getPassword();
                System.out.println(username + "Here it is the username asked for !!!");
                System.out.println(password + "Here it is the username asked for !!!");

                // Create authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
                return this.getAuthenticationManager().authenticate(authToken);

            } catch (IOException e) {
                throw new RuntimeException("Failed to parse authentication request", e);
            }
        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                FilterChain chain, Authentication authResult) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("User logged in successfully");
        }

        @Override
        protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  AuthenticationException failed) throws IOException {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid username or password");
        }
    }
    private static class UserCredentials {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}