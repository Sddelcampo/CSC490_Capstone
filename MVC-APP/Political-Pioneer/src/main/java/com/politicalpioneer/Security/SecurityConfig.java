package com.politicalpioneer.Security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    // private UserDatailService userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
    HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    requestCache.setMatchingRequestParameterName(null);
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((authorize) -> authorize
            .dispatcherTypeMatchers(DispatcherType.FORWARD,
                DispatcherType.ERROR)
            .permitAll()
            .requestMatchers("/", "/home", "/user", "/users", "/user/userName/**", "/parties/**", "/party/**", "/users/role/user", "/questionnaire", "/account", "/admin", 
            "/acctlogin", "/signup", "/platform", "/resources", "/events", "/aboutus", "/bugreport", "/contactdevteam", "/guide").permitAll()
            .requestMatchers("/css/**", "/images/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/user").permitAll()
             .requestMatchers("/admin").hasAuthority("admin")
            .anyRequest().authenticated())
        .formLogin(form -> form.defaultSuccessUrl("/admin", true).permitAll())
        .exceptionHandling((x) -> x.accessDeniedPage("/403"))
        .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
        .requestCache((cache) -> cache
            .requestCache(requestCache));

    return http.build();
  } 

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

    
}
