package com.romaxit.dev.autenticador.services.impl;

import com.romaxit.dev.autenticador.domain.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

//    @SneakyThrows
//    @Transactional
    public UserDetails getUserByUser(UserEntity userEntity) throws UsernameNotFoundException {

        List<GrantedAuthority> authorities = userEntity.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());

        final UserDetails userDetails = new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                authorities
        );

        //UserDetails user = new User(username, "password", true, true, true, true, new GrantedAuthority[]{ new GrantedAuthorityImpl("ROLE_USER") });
        //return user;
        return userDetails;
    }
}
