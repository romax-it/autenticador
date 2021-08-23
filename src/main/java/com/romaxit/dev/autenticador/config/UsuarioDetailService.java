package com.romaxit.dev.autenticador.config;

import com.romaxit.dev.autenticador.config.model.UserPrincipal;
import com.romaxit.dev.autenticador.core.exceptions.ResourceNotFoundException;
import com.romaxit.dev.autenticador.domain.entities.Usuario;
import com.romaxit.dev.autenticador.services.impl.UsuarioService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// TODO: Auto-generated Javadoc
/**
 * The Class UsuarioDetailService.
 */
@Service
public class UsuarioDetailService implements UserDetailsService {

    /** The user repository. */
    @Autowired
    UsuarioService usuarioService;

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @SneakyThrows
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = usuarioService.findEntityByUsername(username);

        if (user == null ) {
            throw new ResourceNotFoundException( String.format(" No fue posible consultar el Usuario: %s", username) );
        }
        List<GrantedAuthority> authorities = user.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());

        return new UserPrincipal(
                (long) user.getId(),
                user.getNombre() + " " + user.getApellido(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}