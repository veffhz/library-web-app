package ru.otus.librarywebapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import java.util.Collections;
import java.util.List;

@EnableWebFluxSecurity
public class SecurityConfiguration  {

    private RoleHierarchyVoter roleHierarchyVoter;

    private final CustomReactiveUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(CustomReactiveUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void init() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/api/**").access(this::isUser)
                .pathMatchers(HttpMethod.PUT, "/api/**").access(this::isUser)
                .pathMatchers(HttpMethod.POST, "/api/**").access(this::isUser)
                .pathMatchers(HttpMethod.DELETE, "/api/**").access(this::isAdmin)
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

    private Mono<AuthorizationDecision> isUser(Mono<Authentication> authentication,
                                               AuthorizationContext authorizationContext) {
        List<ConfigAttribute> attributes = Collections.singletonList((ConfigAttribute) () -> "ROLE_USER");
        return authentication
                .map(auth -> roleHierarchyVoter.vote(auth, authorizationContext, attributes))
                .map(result -> AccessDecisionVoter.ACCESS_GRANTED == result)
                .map(AuthorizationDecision::new).defaultIfEmpty(new AuthorizationDecision(false));
    }

    private Mono<AuthorizationDecision> isAdmin(Mono<Authentication> authentication,
                                               AuthorizationContext authorizationContext) {
        List<ConfigAttribute> attributes = Collections.singletonList((ConfigAttribute) () -> "ROLE_ADMIN");
        return authentication
                .map(auth -> roleHierarchyVoter.vote(auth, authorizationContext, attributes))
                .map(result -> AccessDecisionVoter.ACCESS_GRANTED == result)
                .map(AuthorizationDecision::new).defaultIfEmpty(new AuthorizationDecision(false));
    }

}
