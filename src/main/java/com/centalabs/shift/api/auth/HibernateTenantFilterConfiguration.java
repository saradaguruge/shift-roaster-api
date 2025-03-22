package com.centalabs.shift.api.auth;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

@Component
@Aspect
@AllArgsConstructor
public class HibernateTenantFilterConfiguration {

    private final EntityManager em;

    // Pointcut targeting the every repository's find methods
    @Before("execution(* com.centalabs.shift.api.repository..*.find*(..))")
    public void logAfterReturning() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Session hibernateSession = em.unwrap(Session.class);
            Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
            if (auth instanceof AuthUserToken authUserToken) {
                UUID tenantId = authUserToken.getTenant().getId();
                hibernateSession.enableFilter("tenantFilter").setParameter("tenantId", tenantId.toString());
                hibernateSession.enableFilter("deleteFilter").setParameter("deleted", false);
            }

        }
    }
}