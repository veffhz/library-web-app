package ru.otus.librarywebapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class CustomAuthorizationDecisionManger implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final RoleHierarchyVoter roleHierarchyVoter;

    @Autowired
    public CustomAuthorizationDecisionManger(RoleHierarchyVoter roleHierarchyVoter) {
        this.roleHierarchyVoter = roleHierarchyVoter;
    }

    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication.map((a) -> new AuthorizationDecision(a.isAuthenticated()))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    public Mono<AuthorizationDecision> isUser(Mono<Authentication> authentication,
                                               AuthorizationContext authorizationContext) {
        List<ConfigAttribute> attributes = Collections.singletonList((ConfigAttribute) () -> "ROLE_USER");
        return authentication
                .map(auth -> roleHierarchyVoter.vote(auth, authorizationContext, attributes))
                .map(result -> AccessDecisionVoter.ACCESS_GRANTED == result)
                .map(AuthorizationDecision::new).defaultIfEmpty(new AuthorizationDecision(false));
    }

    public Mono<AuthorizationDecision> isAdmin(Mono<Authentication> authentication,
                                                AuthorizationContext authorizationContext) {
        List<ConfigAttribute> attributes = Collections.singletonList((ConfigAttribute) () -> "ROLE_ADMIN");
        return authentication
                .map(auth -> roleHierarchyVoter.vote(auth, authorizationContext, attributes))
                .map(result -> AccessDecisionVoter.ACCESS_GRANTED == result)
                .map(AuthorizationDecision::new).defaultIfEmpty(new AuthorizationDecision(false));
    }
}
