package com.tuapp.grocery_backend.service;

import com.tuapp.grocery_backend.model.Usuario;
import com.tuapp.grocery_backend.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Optional<Usuario> login(String email, String rawPassword) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(rawPassword, usuario.getPassword())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    public Usuario registrar(Usuario usuario, String rawPassword) {
        usuario.setPassword(passwordEncoder.encode(rawPassword));
        usuario.setFechaCreacion(new java.sql.Timestamp(System.currentTimeMillis())); // <--- aquí
        return usuarioRepository.save(usuario);
    }
}