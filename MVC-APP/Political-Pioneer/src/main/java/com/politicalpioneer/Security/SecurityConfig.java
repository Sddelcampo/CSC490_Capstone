package com.politicalpioneer.Security;

// import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import jakarta.servlet.DispatcherType;


import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailService userDetailService;
    private final AdminDetailService adminDetailService;

    public SecurityConfig(UserDetailService userDetailService,
                          AdminDetailService adminDetailService) {
        this.userDetailService = userDetailService;
        this.adminDetailService = adminDetailService;
    }

  
    @Bean
    @Order(1)
    public SecurityFilterChain adminChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/adminLogin", "/admin", "/admin/**")
            .authenticationProvider(adminAuthProvider())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers("/adminLogin").permitAll()
                .anyRequest().hasAuthority("admin"))
            .formLogin(form -> form
                .loginPage("/adminLogin")
                .loginProcessingUrl("/adminLogin")
                .defaultSuccessUrl("/admin", true)
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/"))
            .exceptionHandling(x -> x.accessDeniedPage("/403"));

        return http.build();
    }

  
    @Bean
    @Order(2)
    public SecurityFilterChain userChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        http
            .authenticationProvider(userAuthProvider())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers("/", "/home", "/user", "/users", "/user/userName/**", "/parties/**",
                    "/party/**", "/users/role/user", "/questionnaire", "/signup", "/platform",
                    "/resources", "/events", "/aboutus", "/bugreport", "/contactdevteam", "/guide",
                    "/userSignup", "/getAllAdmin", "/adminUsername/**", "/createAdmin", "/addAdmin").permitAll()
                .requestMatchers("/css/**", "/images/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers("/account").hasAnyAuthority("owner", "user", "admin")
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/acctlogin")
                .defaultSuccessUrl("/platform", true)
                .permitAll())
            .exceptionHandling(x -> x.accessDeniedPage("/403"))
            .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
            .requestCache(cache -> cache.requestCache(requestCache));

        return http.build();
    }

    
    private DaoAuthenticationProvider userAuthProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider(userDetailService);
        p.setPasswordEncoder(passwordEncoder());
        return p;
    }

    private DaoAuthenticationProvider adminAuthProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider(adminDetailService);
        p.setPasswordEncoder(passwordEncoder());
        return p;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}