package com.romaxit.dev.autenticador.controllers;

import com.romaxit.dev.autenticador.core.auxiliar.ResultSearchData;
import com.romaxit.dev.autenticador.core.exceptions.ResourceNotFoundException;
import com.romaxit.dev.autenticador.core.exceptions.UnauthorizedRequestException;
import com.romaxit.dev.autenticador.domain.dto.MailDto;
import com.romaxit.dev.autenticador.domain.dto.RoleDto;
import com.romaxit.dev.autenticador.domain.dto.UsuarioDto;
import com.romaxit.dev.autenticador.services.IUsuarioService;
import com.romaxit.dev.autenticador.services.impl.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping({ "v1/usuario-api" })
@CrossOrigin
public class UsuarioController {

    @Autowired
    private IUsuarioService<UsuarioDto> usuarioService;

    private IEmailService emailService;

    @Autowired
    public UsuarioController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping(value = "/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UsuarioDto> create(@RequestBody @Valid UsuarioDto usuario) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.create(usuario), HttpStatus.OK);
    }

    @GetMapping("/findByToken")
    public ResponseEntity<UsuarioDto> findByToken(@RequestHeader(name = "Authorization") String token) throws ResourceNotFoundException {
        token = token.replaceAll("Bearer ","");
        return new ResponseEntity<>(usuarioService.findByToken(token), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UsuarioDto> update(@RequestBody @Valid UsuarioDto Usuario) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.update(Usuario), HttpStatus.OK);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Object> delete(@RequestParam int id) throws ResourceNotFoundException {
        usuarioService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Iterable<UsuarioDto>> findAll() {
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/findAllStudends")
    public ResponseEntity<Iterable<UsuarioDto>> findAllStudends() {
        return new ResponseEntity<>(usuarioService.findAllStudends(), HttpStatus.OK);
    }

    @GetMapping("/findAllTeachers")
    public ResponseEntity<Iterable<UsuarioDto>> findAllTeachers() {
        return new ResponseEntity<>(usuarioService.findAllTeachers(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ResultSearchData<UsuarioDto>> search(@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "sortBy") String sortBy
            , @RequestParam(name = "sortOrder") String sortOrder ) {
        ResultSearchData<UsuarioDto> datos = usuarioService.findAllSearch(page, size,sortBy, sortOrder);
        return new ResponseEntity<ResultSearchData<UsuarioDto>>(datos, new HttpHeaders(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/getRoles")
    public ResponseEntity<List<String>> getRoles() throws UnauthorizedRequestException {
        UsuarioDto usuarioLogueado = usuarioService.getUserLogged();
        List<String> roles =  usuarioLogueado.getRoles().stream().map(RoleDto::getNombre).collect(Collectors.toList());
        return  ResponseEntity.ok(roles);
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/pruebacorreo")
    public void pruebaCorreo(){
        MailDto mailDto = new MailDto("soportecarlosromero@gmail.com","soportecarlosromero@gmail.com", "prueba", "mensaje de prueba");
        emailService.sendMail(mailDto);
    }
}
