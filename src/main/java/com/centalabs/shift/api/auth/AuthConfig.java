package com.centalabs.shift.api.auth;

import com.centalabs.shift.api.domain.User;
import com.centalabs.shift.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class AuthConfig {
    private static final String[] WHITE_LIST_URLS = new String[]{
            "/swagger-ui/*",
            "/v3/api-docs",
            "/v3/api-docs/*",
            "/actuator/**"
    };

    private static final String[] WHITE_LIST_POST_URLS = new String[]{
    };

    private static final String[] WHITE_LIST_GET_URLS = new String[]{
    };

    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UserDetailsService userDetailsService) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        for (String method : "PUT, DELETE, OPTIONS".split(",")) {
            corsConfiguration.addAllowedMethod(method.trim());
        }
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/", corsConfiguration);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(cors -> cors.configurationSource(configurationSource));

        httpSecurity.authorizeHttpRequests((authorize) ->
                authorize.
                        requestMatchers(WHITE_LIST_URLS).permitAll()
                        .requestMatchers(HttpMethod.POST, WHITE_LIST_POST_URLS).permitAll()
                        .requestMatchers(HttpMethod.GET, WHITE_LIST_GET_URLS).permitAll()
                        .requestMatchers(HttpMethod.POST,"/tenants").hasAnyRole("GUEST_USER")
                        .anyRequest().hasAnyRole("TENANT_USER","SUPER_ADMIN")
        ).oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthenticationConverter(userDetailsService)))
        );
        return httpSecurity.build();
    }

    private User createNewUser(Jwt jwt) {
        User user = new User();
//        user.setExternalId(jwt.getSubject());
//        user.setEmail(jwt.getClaimAsString("email"));
        return userRepository.save(user);
    }

}
