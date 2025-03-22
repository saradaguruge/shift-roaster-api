package com.centalabs.shift.api.auth;

import com.centalabs.shift.api.domain.Tenant;
import com.centalabs.shift.api.domain.User;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class AuthUserToken extends AbstractAuthenticationToken {
    private final Tenant tenant;
    private final String token;
    private final User user;

    public AuthUserToken(User user, Collection<? extends GrantedAuthority> authorities, String token) {
        super(authorities);
        this.user = user;
        this.tenant = user.getTenant();
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
}
