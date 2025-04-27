package org.example.shubackend.service;

import lombok.RequiredArgsConstructor;
import org.example.shubackend.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var u = repo.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("user"));
        var auths = u.getRoles().stream()
               .flatMap(r -> r.getPermissions().stream())
               .map(p -> new SimpleGrantedAuthority(p.getName().name()))
               .toList();
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), auths);
    }
}
