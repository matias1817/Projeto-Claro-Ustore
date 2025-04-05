package com.claro.projeto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.claro.projeto.model.user.User;
import com.claro.projeto.model.user.UserService;
import com.claro.projeto.security.JwtAuthenticationEntryPoint;
import com.claro.projeto.security.JwtTokenAuthenticationFilter;
import com.claro.projeto.security.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/webjars/**",
            "/routes/**",
            "/favicon.ico",
            "/ws/**",
    };

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
            UserService userDetailService) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     http
            .cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/user").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/machine").hasAnyAuthority(User.ROLE_USER)
                .requestMatchers(HttpMethod.PUT, "/api/machine/*").hasAnyAuthority(User.ROLE_USER)
                .requestMatchers(HttpMethod.DELETE, "/api/machine/*").hasAnyAuthority(User.ROLE_USER)
                .requestMatchers(HttpMethod.GET, "/api/machine/").hasAnyAuthority(User.ROLE_USER)
              

                .requestMatchers(HttpMethod.PUT, "/api/user/*").hasAnyAuthority(User.ROLE_USER)
                .requestMatchers(HttpMethod.DELETE, "/api/user/*").hasAnyAuthority(User.ROLE_USER)
                .requestMatchers(HttpMethod.GET, "/api/user").hasAnyAuthority(User.ROLE_USER)
                .requestMatchers(HttpMethod.GET, "/api/user/*").hasAnyAuthority(User.ROLE_USER)
            )
            
            .addFilterBefore(new JwtTokenAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
}
    // @Bean
    // public BCryptPasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }


    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}