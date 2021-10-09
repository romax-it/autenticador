package com.romaxit.dev.autenticador.config;

import com.romaxit.dev.autenticador.config.model.JwtResponse;
import com.romaxit.dev.autenticador.config.model.Login;
import com.romaxit.dev.autenticador.core.exceptions.ResourceNotFoundException;
import com.romaxit.dev.autenticador.core.exceptions.UnauthorizedRequestException;
import com.romaxit.dev.autenticador.domain.entities.Usuario;
import com.romaxit.dev.autenticador.services.impl.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthorizationController.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    /** The ldap properties. */
//    @Value("${spring.ldap.properties}")
//    private String ldapProperties;

    /** The authentication manager. */
    @Autowired
    AuthenticationManager authenticationManager;

    /** The user repository. */
    @Autowired
    UsuarioService usuarioService;

    /** The lista item repository. */
//    @Autowired
//    RepositoryListaItem listaItemRepository;

    /** The jwt provider. */
    @Autowired
    JwtProvider jwtProvider;

    /** The ldap authentication manager. */
//    @Autowired
//    UmvLdapAuthenticationProvider ldapAuthenticationManager;


    /**
     * Authenticate.
     *
     * @param loginRequest the login request
     * @return the response entity
     * @throws UnauthorizedRequestException the unauthorized request exception
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody Login loginRequest) throws UnauthorizedRequestException {
        Authentication authentication = null;
        int contador = 0;
        if (loginRequest.getUsername() != null) {
            Usuario user = null;
            try {
                user = usuarioService.findEntityByUsername(loginRequest.getUsername());
            } catch (Exception e) {
                throw new UnauthorizedRequestException("Error al consultar información del usuario");
            }
            if (user != null) {
                if (user.getIntentosFallidos() > 3) {
                    throw new UnauthorizedRequestException("Ha superado la cantidad de inténtos permitidos");
                }
                try {
                    String clave = passwordEncoder.encode(loginRequest.getPassword());
                    authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()));
                } catch (AuthenticationException e) {
                    throw new UnauthorizedRequestException("Usuario o clave incorrectos. Si el problema persiste, favor intente restablecer contraseña");
                }

                if (authentication.isAuthenticated()) {
                    int exitososPrevios = user.getIntentosExitosos();
                    contador = exitososPrevios + 1;
                    user.setIntentosExitosos(contador);
                    user.setIntentosFallidos(0);
                } else {
                    int fallidosPrevios = user.getIntentosFallidos();
                    contador = fallidosPrevios + 1;
                    user.setIntentosFallidos(contador);
                }
                try {
                    usuarioService.updateEntity(user);
                } catch (ResourceNotFoundException e) {
                    throw new UnauthorizedRequestException("El usuario no esta activo en el sistema.");
                }

            } else {
                throw new UnauthorizedRequestException("Usuario o clave incorrectos. Favor reviselos e inténtelo nuevamente");
            }
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    /**
     * Unauthorized request handler.
     *
     * @param e the e
     * @return the list
     */
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(UnauthorizedRequestException.class)
//    public List<FieldErrorMessage> unauthorizedRequestHandler(UnauthorizedRequestException e) {
//        FieldErrorMessage error = new FieldErrorMessage();
//        error.setMessage(e.getMessage());
//        List<FieldErrorMessage> fieldErrors = new ArrayList<FieldErrorMessage>();
//        fieldErrors.add(error);
//        return fieldErrors;
//    }

}