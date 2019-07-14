package ru.otus.librarywebapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@EnableWebFluxSecurity
public class SecurityConfiguration  {

    private final CustomReactiveUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(CustomReactiveUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public RoleHierarchyVoter roleHierarchyVoter() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return new RoleHierarchyVoter(roleHierarchy);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, CustomAuthorizationDecisionManger authorizationDecisionManger) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/api/**").access(authorizationDecisionManger::isUser)
                .pathMatchers(HttpMethod.PUT, "/api/**").access(authorizationDecisionManger::isUser)
                .pathMatchers(HttpMethod.POST, "/api/**").access(authorizationDecisionManger::isUser)
                .pathMatchers(HttpMethod.DELETE, "/api/**").access(authorizationDecisionManger::isAdmin)
                .pathMatchers(HttpMethod.PUT, "/task/**").access(authorizationDecisionManger::isAdmin)
                .anyExchange().authenticated()
                .and().formLogin()
                .authenticationManager(authenticationManager())
                .securityContextRepository(securityContextRepository())
                .and().build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager manager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        manager.setPasswordEncoder(passwordEncoder());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSessionServerSecurityContextRepository securityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }

}
