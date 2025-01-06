package com.shoppingcart.eBazzar.security.config;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shoppingcart.eBazzar.security.jwt.AuthFilter;
import com.shoppingcart.eBazzar.security.jwt.JwtEntryPoint;
import com.shoppingcart.eBazzar.security.user.ShopUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final ShopUserDetailsService userDetailsService;
    private final JwtEntryPoint jwtEntryPoint;

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {
    // return http
    // .csrf(AbstractHttpConfigurer::disable)
    // .cors(cors -> cors.configurationSource(corsConfigurationSource()))
    // .authorizeHttpRequests(auth -> auth
    // .requestMatchers("/uploads/**").permitAll()
    // .requestMatchers("/api/v1/auth/**").permitAll()
    // .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
    // .requestMatchers("/api/v1/customer/**").hasAnyRole("ADMIN", "CUSTOMER")
    // .requestMatchers("/api/v1/user/**").hasAnyRole("ADMIN", "CUSTOMER", "USER")
    // .anyRequest().authenticated())
    // .sessionManagement(session ->
    // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    // .authenticationProvider(authenticationProvider())
    // .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
    // .exceptionHandling(exception -> exception
    // .authenticationEntryPoint((request, response, authException) -> {
    // System.out.println("Authentication failed: " + authException.getMessage());
    // response.sendError(HttpStatus.UNAUTHORIZED.value(),
    // authException.getMessage());
    // }))
    // .build();
    // }

    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    // CorsConfiguration configuration = new CorsConfiguration();
    // configuration.setAllowedOrigins(Arrays.asList("*"));
    // configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
    // "OPTIONS"));
    // configuration.setAllowedHeaders(Arrays.asList("*"));
    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();
    // source.registerCorsConfiguration("/**", configuration);
    // return source;
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder(12);
    // }

    // @Bean
    // public AuthenticationManager authenticationManager(
    // AuthenticationConfiguration config) throws Exception {
    // return config.getAuthenticationManager();
    // }

    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    // DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    // provider.setPasswordEncoder(passwordEncoder());
    // provider.setUserDetailsService(userDetailsService);
    // return provider;
    // }

    //////
    ///
    ///
    private static final List<String> SECURED_URLS = List.of(
            "/api/v1/carts/**",
            "/api/v1/cartItems/**",
            "/api/v1/orders/**");

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthFilter authTokenFilter() {
        return new AuthFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();

    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
                        .anyRequest().permitAll());
        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
