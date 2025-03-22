package com.centalabs.shift.api.auth;

import com.centalabs.shift.api.Constants;
import com.centalabs.shift.api.domain.User;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserDetailsService userDetailsService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractAuthorities(jwt);
        try {
            User user = (User) userDetailsService.loadUserByUsername(jwt.getSubject());
//            if (!UUID.fromString(jwt.getClaim(TENANT_ID)).equals(user.getTenant().getId())) {
//                throw new AccessDeniedException("Invalid tenant matching");
//            }
            ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>(authorities);
            grantedAuthorities.add(new SimpleGrantedAuthority(Constants.TENANT_USER));
            AuthUserToken authenticationToken = new AuthUserToken(user, grantedAuthorities, jwt.getTokenValue());
            authenticationToken.setAuthenticated(true);
            return authenticationToken;
        } catch (UsernameNotFoundException e) {
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt, List.of(new SimpleGrantedAuthority(Constants.GUEST_USER)));
            jwtAuthenticationToken.setAuthenticated(true);
            return jwtAuthenticationToken;
        }
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<String> claim = jwt.getClaim(Constants.ROLE);
        return claim == null ? Collections.emptyList() : claim.stream().map(String::toUpperCase).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}