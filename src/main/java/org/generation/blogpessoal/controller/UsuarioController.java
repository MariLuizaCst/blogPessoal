package org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.model.UserLogin;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.generation.blogpessoal.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @GetMapping("/all")
    public ResponseEntity <List<Usuario>> getAll(){     
        return ResponseEntity.ok(usuarioRepository.findAll());  
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return usuarioRepository.findById(id)
            .map(resposta -> ResponseEntity.ok(resposta))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/logar")
    public ResponseEntity<Usuario> login(@RequestBody Optional<UserLogin> usuarioLogin) {
        return usuarioService.autenticarUsuario(usuarioLogin)
            .map(resposta -> ResponseEntity.ok(resposta))
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @SuppressWarnings("unchecked")
	@PostMapping("/cadastrar")
    public ResponseEntity<Usuario> postUsuario(@Valid @RequestBody Usuario usuario) {

    	return (ResponseEntity<Usuario>) ResponseEntity.status(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuario(usuario)
            .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}