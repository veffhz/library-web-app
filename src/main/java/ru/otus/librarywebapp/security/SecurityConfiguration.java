package ru.otus.librarywebapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import ru.otus.librarywebapp.dao.UserRepository;

@EnableWebFluxSecurity
public class SecurityConfiguration  {

//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        UserDetails admin = User
//                .withUsername("adm")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER", "ADMIN")
//                .build();
//        UserDetails user = User
//                .withUsername("usr")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//        return new MapReactiveUserDetailsService(admin, user);
//    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ROLE_USER")
                .pathMatchers(HttpMethod.POST, "/api/**").hasAuthority("ROLE_USER")
                .pathMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ROLE_ADMIN")
                .anyExchange().authenticated()
                .and().formLogin()
                .and().build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(UserRepository userRepository) {
        UserDetailsRepositoryReactiveAuthenticationManager manager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userRepository);
        manager.setPasswordEncoder(passwordEncoder());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
