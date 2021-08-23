package com.romaxit.dev.autenticador.config;

import com.romaxit.dev.autenticador.core.ConstantesAutenticador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


// TODO: Auto-generated Javadoc
/**
 * The Class JwtAuthTokenFilter.
 */
public class JwtAuthTokenFilter extends OncePerRequestFilter
{

    /** The token provider. */
    @Autowired
    private JwtProvider tokenProvider;

    /** The user details service. */
    @Autowired
    private UsuarioDetailService userDetailsService;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    /* (non-Javadoc)
     * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
//    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String ruta = request.getRequestURI();
        if( ! esRutasAdmitida(ruta)){
            String jwt = getJwt(request);
            if (jwt == null ) {
                logger.error("No se encontro el token de autenticación, favor inicie sesión");
                throw new AuthenticationException("No se encontro el token de autenticación, favor inicie sesión");
            }
            if (!tokenProvider.validateJwtToken(jwt)) {
                logger.error("El token es invalido o ");
                throw new AuthenticationException("El token es invalido, favor inicie sesión");
            }
            String username = tokenProvider.getUserNameFromJwtToken(jwt);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private boolean esRutasAdmitida(String ruta) {
        String rutasAdmitidas = ConstantesAutenticador.Seguridad.RUTAS_ADMITIDAS;
        String[] rutasAdmitidasSeguridad = rutasAdmitidas.split(",");

        if(Arrays.stream(rutasAdmitidasSeguridad).filter( r -> r.contains(ruta)).findFirst().isPresent()){
            return true;
        }
        return false;
    }

    /**
     * Gets the jwt.
     *
     * @param request the request
     * @return the jwt
     */
    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }
}
