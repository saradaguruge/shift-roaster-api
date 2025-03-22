package com.centalabs.shift.api.service;

import com.centalabs.shift.api.domain.User;
import com.centalabs.shift.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByExternalId(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
