package com.centalabs.shift.api.auth;

import com.centalabs.shift.api.domain.Tenant;
import com.centalabs.shift.api.domain.TenantBaseEntity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@EntityListeners(EntityListener.class)
public class EntityListener {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PrePersist
    public void setTenant(Object entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (entity instanceof TenantBaseEntity tenantBaseEntity && authentication instanceof AuthUserToken apiAuthenticationToken) {
            Tenant tenant = apiAuthenticationToken.getTenant();
            if (tenant != null) {
                tenantBaseEntity.setTenant(tenant);
            }
        }
    }
}


